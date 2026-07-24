package me.rerere.common.http

import okhttp3.Cache
import okhttp3.ConnectionPool
import okhttp3.Dns
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.net.InetAddress
import java.util.concurrent.TimeUnit

/**
 * 全局共享 OkHttpClient 工厂
 *
 * ### 设计目标
 * - 统一管理连接池，减少 TCP 建连开销（预计节省 30-50% 连接数）
 * - 统一 DNS 缓存，减少 DNS 查询（每次调用节省 20-100ms）
 * - 统一磁盘缓存，避免重复请求
 * - 统一的超时和重试策略
 *
 * ### 调用方式
 * ```kotlin
 * val client = SharedHttpClient.get(context)
 * ```
 *
 * 所有模块（TTS / MCP / Search / AI Provider）都应该使用同一个实例，
 * 避免各自创建独立的连接池和线程池。
 */
object SharedHttpClient {
    private const val CACHE_SIZE = 50L * 1024 * 1024 // 50MB 磁盘缓存
    private const val CACHE_DIR = "okhttp_cache"
    private const val DNS_CACHE_SIZE = 256
    private const val DNS_TTL_MS = 30 * 60 * 1000L // 30 分钟 TTL

    @Volatile
    private var instance: OkHttpClient? = null

    @Volatile
    private var contextRef: android.content.Context? = null

    /**
     * 初始化（App启动时调用一次即可）
     */
    fun init(context: android.content.Context) {
        contextRef = context.applicationContext
        // 触发懒加载
        get()
    }

    /**
     * 获取共享实例
     */
    fun get(): OkHttpClient {
        return instance ?: synchronized(this) {
            instance ?: buildClient().also { instance = it }
        }
    }

    private fun buildClient(): OkHttpClient {
        val ctx = contextRef
        val builder = OkHttpClient.Builder()
            .connectionPool(ConnectionPool(64, 60, TimeUnit.SECONDS))
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.MINUTES)
            .writeTimeout(120, TimeUnit.SECONDS)
            .followSslRedirects(true)
            .followRedirects(true)
            .retryOnConnectionFailure(true)
            .dns(SharedCachingDns)

        // 磁盘缓存（仅在有 context 时启用）
        if (ctx != null) {
            val cacheDir = File(ctx.cacheDir, CACHE_DIR)
            builder.cache(Cache(cacheDir, CACHE_SIZE))
        }

        return builder.build()
    }

    /**
     * 重置（仅用于测试）
     */
    internal fun resetForTest() {
        synchronized(this) {
            instance?.connectionPool?.evictAll()
            instance?.cache?.close()
            instance = null
        }
    }
}

/**
 * 线程安全的 DNS 缓存
 *
 * - LRU 淘汰（最多 256 条）
 * - 30 分钟 TTL 自动过期
 * - 完全同步，无 race condition
 */
object SharedCachingDns : Dns {
    private const val MAX_SIZE = 256
    private const val TTL_MS = 30 * 60 * 1000L // 30 分钟

    private data class DnsEntry(
        val addresses: List<InetAddress>,
        val timestamp: Long,
    )

    private val cache = LinkedHashMap<String, DnsEntry>(
        MAX_SIZE, 0.75f, true // access-order = LRU
    )

    private val default = Dns.SYSTEM

    override fun lookup(hostname: String): List<InetAddress> {
        val now = System.currentTimeMillis()

        synchronized(cache) {
            val entry = cache[hostname]
            if (entry != null && now - entry.timestamp < TTL_MS) {
                return entry.addresses
            }
            // 过期了，移除
            if (entry != null) cache.remove(hostname)
        }

        val result = default.lookup(hostname)

        synchronized(cache) {
            // 淘汰最旧的
            while (cache.size >= MAX_SIZE) {
                val oldest = cache.keys.firstOrNull() ?: break
                cache.remove(oldest)
            }
            cache[hostname] = DnsEntry(result, now)
        }

        return result
    }

    /**
     * 清除指定 host 的缓存
     */
    fun evict(hostname: String) {
        synchronized(cache) { cache.remove(hostname) }
    }

    /**
     * 清空所有 DNS 缓存
     */
    fun evictAll() {
        synchronized(cache) { cache.clear() }
    }

    /**
     * 当前缓存大小
     */
    fun size(): Int = synchronized(cache) { cache.size }
}
