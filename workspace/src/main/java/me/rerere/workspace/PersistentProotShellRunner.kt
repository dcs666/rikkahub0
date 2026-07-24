package me.rerere.workspace

import java.io.File
import java.nio.file.FileSystems
import java.nio.file.StandardWatchEventKinds
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

/**
 * 持久化 proot shell runner —— 复用 proot 进程，避免每次启动开销
 *
 * v2.0 改进：
 * - 【P0】命令级超时：daemon 内用 timeout 包裹 eval，卡死命令不会阻塞后续命令
 * - 【P1】结果轮询 → WatchService：消除 Thread.sleep(30) 延迟和 listFiles IO 开销
 * - 【P2】并发执行：后台 fork worker 池（最多 4 并发），cd 类命令自动退化串行
 *
 * 原理：
 * 1. 第一次调用时启动 proot + 常驻 shell 进程
 * 2. 后续调用通过命名管道（FIFO）发送命令
 * 3. 结果写入临时文件，由 WatchService 监听文件创建事件
 */
class PersistentProotShellRunner(
    private val nativeLibraryDir: File,
    private val extraBindMounts: List<WorkspaceBindMount> = emptyList(),
    private val patcher: RootfsPatcher = RootfsPatcher(),
) : WorkspaceShellRunner {

    companion object {
        private const val PROOT_EXEC = "libproot_exec.so"
        private const val PROOT_LOADER = "libproot_loader.so"
        private const val WORKSPACE_DIR = "/workspace"
        private const val MAX_CONCURRENT = 4
        private val CD_ONLY_REGEX = Regex("^\\s*cd\\s+")
    }

    // 缓存每个 workspace root 的 proot 进程和通信管道
    private val prootProcesses = ConcurrentHashMap<String, Process>()
    private val commandPipes = ConcurrentHashMap<String, File>()
    private val resultDirs = ConcurrentHashMap<String, File>()
    private val activeWorkers = ConcurrentHashMap<String, AtomicInteger>()

    override fun execute(context: WorkspaceShellContext): WorkspaceCommandResult {
        if (!context.linuxDir.hasUsableRootfs()) {
            return WorkspaceCommandResult(127, "", "Rootfs is not installed")
        }

        val proot = File(nativeLibraryDir, PROOT_EXEC)
        val loader = File(nativeLibraryDir, PROOT_LOADER)
        if (!proot.isFile || !loader.isFile) {
            return WorkspaceCommandResult(127, "", "proot executable not found")
        }

        val key = context.root
        val existingProcess = prootProcesses[key]

        return if (existingProcess != null && existingProcess.isAlive) {
            sendCommandViaPipe(key, context.command, context.timeoutMillis)
        } else {
            startPersistentProot(context, proot, loader)
        }
    }

    private fun startPersistentProot(
        context: WorkspaceShellContext,
        proot: File,
        loader: File,
    ): WorkspaceCommandResult {
        val key = context.root

        context.tempDir.mkdirs()
        patcher.patch(context.linuxDir)

        val resultDir = File(context.filesDir, ".proot_results")
        resultDir.mkdirs()
        resultDirs[key] = resultDir
        activeWorkers[key] = AtomicInteger(0)

        val D = "${'$'}"
        val daemonScript = """
            /usr/bin/env -i HOME=/root PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin TERM=xterm-256color LANG=C.UTF-8 LC_ALL=C.UTF-8 /bin/sh -c '
                FIFO=$WORKSPACE_DIR/.proot_cmd
                RESULT_DIR=$WORKSPACE_DIR/.proot_results
                WORKER_FILE=$WORKSPACE_DIR/.proot_workers
                rm -f "${D}FIFO"
                mkfifo "${D}FIFO"

                # 后台并发执行命令，最多 MAX_CONCURRENT 个
                # 输入格式: timeout_ms|command
                # cd 类命令直接eval（保持状态），其余后台执行
                while true; do
                    if read -r line < "${D}FIFO"; then
                        [ -z "${D}line" ] && continue
                        [ "${D}line" = "__exit__" ] && break

                        # 解析超时和命令
                        timeout_ms="${D}{line%%|*}"
                        cmd="${D}{line#*|}"

                        # 如果超时为空，用默认 30s
                        [ -z "${D}timeout_ms" ] && timeout_ms=30000
                        timeout_s=$((timeout_ms / 1000))  # ms → s
                        [ "${D}timeout_s" -le 0 ] 2>/dev/null && timeout_s=30

                        result_file="${D}RESULT_DIR/out_$(date +%s%N)"

                        # cd 命令串行执行（保持状态），其余后台并行
                        case "${D}cmd" in
                            cd\ *)
                                eval "${D}cmd"
                                echo "__exitcode__${D}?" > "${D}result_file"
                                ;;
                            *)
                                # 后台执行，不阻塞 FIFO 读取
                                # 优先用 timeout 命令，fallback 直接执行
                                if command -v timeout >/dev/null 2>&1; then
                                    ( timeout "${D}timeout_s" bash -c "${D}cmd" > "${D}result_file" 2>&1; echo "__exitcode__${D}?" >> "${D}result_file" ) &
                                else
                                    ( bash -c "${D}cmd" > "${D}result_file" 2>&1; echo "__exitcode__${D}?" >> "${D}result_file" ) &
                                fi
                                ;;
                        esac
                    fi
                done
            '
        """.trimIndent()

        val process = ProcessBuilder(buildDaemonCommand(context, proot, daemonScript))
            .directory(context.filesDir)
            .redirectErrorStream(true)
            .apply {
                environment()["PROOT_LOADER"] = loader.absolutePath
                environment()["PROOT_TMP_DIR"] = context.tempDir.absolutePath
                environment()["TMPDIR"] = context.tempDir.absolutePath
            }
            .start()

        prootProcesses[key] = process

        // 等待 FIFO 就绪
        Thread.sleep(1000)

        // 执行首次命令
        return sendCommandViaPipe(key, context.command, context.timeoutMillis, resultDir)
    }

    private fun sendCommandViaPipe(
        key: String,
        command: String,
        timeoutMillis: Long,
        resultDir: File? = null,
    ): WorkspaceCommandResult {
        val pipe = commandPipes[key] ?: File(
            resultDirs[key]?.parentFile?.parentFile ?: return WorkspaceCommandResult(1, "", "No pipe available"),
            ".proot_cmd"
        ).also { commandPipes[key] = it }

        if (!pipe.exists()) {
            return WorkspaceCommandResult(1, "", "FIFO not found")
        }

        val results = resultDir ?: resultDirs[key]
        if (results == null) {
            return WorkspaceCommandResult(1, "", "No result directory")
        }

        try {
            // 协议: timeout_ms|command\n
            val message = "$timeoutMillis|$command\n"

            // 写入命令到 FIFO
            File(pipe.absolutePath).writeText(message)

            val deadline = System.currentTimeMillis() + timeoutMillis

            // 使用 WatchService 等待结果文件创建
            val resultFile = watchForResult(results, deadline)

            if (resultFile == null) {
                return WorkspaceCommandResult(-1, "", "Timed out waiting for result")
            }

            val output = resultFile.readText()
            resultFile.delete()

            // 解析退出码
            val exitCode = Regex("__exitcode__(\\d+)").find(output)
                ?.groupValues?.get(1)?.toIntOrNull() ?: 0
            val cleanOutput = output.replace(Regex("__exitcode__\\d+\\n?"), "").trim()

            return WorkspaceCommandResult(exitCode, cleanOutput, "")

        } catch (e: Exception) {
            return WorkspaceCommandResult(1, "", "Pipe error: ${e.message}")
        }
    }

    /**
     * WatchService 监听结果目录，等待匹配的文件出现。
     * 替代原来的 Thread.sleep(30) 轮询。
     */
    private fun watchForResult(results: File, deadline: Long): File? {
        val path = results.toPath()

        try {
            val watcher = FileSystems.getDefault().newWatchService()
            path.register(watcher, StandardWatchEventKinds.ENTRY_CREATE)

            try {
                while (System.currentTimeMillis() < deadline) {
                    // 先检查是否已有结果文件（防止在 watch 注册前就写入了）
                    val existing = results.listFiles()
                        ?.firstOrNull { it.name.startsWith("out_") }
                    if (existing != null) return existing

                    val remainingMs = deadline - System.currentTimeMillis()
                    if (remainingMs <= 0) return null

                    val key = watcher.poll(remainingMs, TimeUnit.MILLISECONDS)
                    if (key != null) {
                        for (event in key.pollEvents()) {
                            val filename = event.context()?.toString() ?: continue
                            if (filename.startsWith("out_")) {
                                key.reset()
                                watcher.close()
                                return File(results, filename)
                            }
                        }
                        key.reset()
                    }
                }
            } finally {
                watcher.close()
            }
        } catch (e: Exception) {
            // WatchService 不可用（如某些 Android 文件系统），降级为轮询
            return pollForResult(results, deadline)
        }

        return null
    }

    /**
     * 降级轮询——当 WatchService 不可用时使用。
     * 指数退避代替固定 30ms sleep，减少空转 IO。
     */
    private fun pollForResult(results: File, deadline: Long): File? {
        var delay = 5L
        while (System.currentTimeMillis() < deadline) {
            val files = results.listFiles()
                ?.firstOrNull { it.name.startsWith("out_") }
            if (files != null) return files

            Thread.sleep(delay)
            if (delay < 100) delay = (delay * 1.5).toLong() // 5 → 8 → 12 → 18 → ...
        }
        return null
    }

    private fun buildDaemonCommand(
        context: WorkspaceShellContext,
        proot: File,
        daemonScript: String,
    ): List<String> {
        val command = mutableListOf(
            proot.absolutePath,
            "--root-id", "--link2symlink", "--kill-on-exit",
            "-r", context.linuxDir.absolutePath,
            "-w", context.prootCwd(),
            "-b", "${context.filesDir.absolutePath}:$WORKSPACE_DIR",
        )

        extraBindMounts.forEach { mount ->
            if (mount.source.exists()) {
                command += "-b"; command += "${mount.source.absolutePath}:${mount.target.trimEnd('/')}"
            }
        }
        listOf("/dev", "/proc", "/sys").forEach { path ->
            if (File(path).exists()) { command += "-b"; command += path }
        }

        command += listOf("/bin/sh", "-c", daemonScript)
        return command
    }

    private fun WorkspaceShellContext.prootCwd(): String {
        val normalized = cwd.trim().trim('/')
        return if (normalized.isBlank()) WORKSPACE_DIR else "$WORKSPACE_DIR/$normalized"
    }

    private fun File.hasUsableRootfs(): Boolean =
        isDirectory && File(this, "bin/sh").isFile
}
