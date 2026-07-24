---
name: 爬虫技能
description: 爬虫知识体系与工作流指南（整合版）。涵盖反爬6级对抗体系、工具链速查、搜索引擎突破实战、完整30节工作流指南、13个实战代码模板。铁律：每次爬取前必须先读本文件，爬取后必须复盘更新。【2026-07新趋势】MCP协议+AI驱动爬取+CDP浏览器自动化+LLM内容提取
---

# 🕷️ 爬虫知识体系 & 工作流指南（整合版 v6）

> **最后更新**: 2026-07-24
> **来源**: 30个GitHub高星项目源码分析（⭐638K+）+ 实战经验
> **铁律**: 每次新对话开始必须先读取本文件；每次爬取完成必须复盘更新

---

## 📋 快速导航

| 章节 | 内容 |
|:-----|:------|
| [反爬6级体系](#-反爬6级对抗体系) | L0-L6 检测维度与应对 |
| [工具链](#-关键工具链) | 推荐工具速查 |
| [搜索引擎实战](#-搜索引擎反爬实战) | 百度/Bing/Google 突破 |
| [工作流指南](#1-工具选择决策树) | 完整30节工作流 |
| [代码模板](#-代码模板库) | 13个实战脚本 |

---

## 🏗️ 反爬6级对抗体系

| 级别 | 检测维度 | 应对手段 |
|:----:|:---------|:---------|
| **L0** | 无检测 | curl / requests 直连 |
| **L1** | UA/Header检查 | 补全17个浏览器头 |
| **L2** | TLS指纹（JA3/JA4） | curl_cffi(⭐6.1K) / curl-impersonate(⭐6.6K) |
| **L3** | JS环境检测 | Playwright + Patchright(⭐3.9K) |
| **L4** | 浏览器指纹（Canvas/WebGL/Audio） | CloakBrowser(⭐28.9K) / Camoufox(⭐10.4K) |
| **L5** | 行为分析 | 贝塞尔鼠标/随机打字/自然滚动 |
| **L6** | 验证码 | Botright AI / 第三方打码API |

---

## 🔑 关键工具链

| 工具 | ⭐ | 用途 |
|:-----|---:|:------|
| **curl** | — | 首选轻量，能直连就不上浏览器 |
| **requests+BS4** | — | 静态HTML解析 |
| **curl_cffi** | 6.1K | Python一行伪装Chrome TLS指纹 |
| **Playwright** | 70K+ | 微软无头浏览器 |
| **Patchright** | 3.9K | Playwright隐身版 |
| **CloakBrowser** | 28.9K | C++源码级修改Chromium（71补丁） |
| **Crawlee** | 24.9K | 会话池+代理分层+自动并发 |
| **Scrapling** | 70.8K | 自适应选择器 |
| **readability** | 11.4K | 正文提取 |

---

## 🏃 4层递进策略（Trawl）

```
Tier1: 纯HTTP（<100ms）
  ↓ 失败（403/反爬）
Tier2: 缓存浏览器会话（~500ms）
  ↓ 失败
Tier3: 新建浏览器解题（4~15s）
  ↓ 失败
Tier4: 住宅代理+浏览器（15~45s）
```

**铁律**: 能快则快，逐层升级

---

## 🔍 搜索引擎反爬实战

| 引擎 | 强度 | 策略 |
|:----|:----:|:------|
| **百度** | ⭐⭐⭐ | 加满17个浏览器头+--compressed；被封切SM |
| **SM（神马搜索）** | ⭐⭐ | **最弱**，curl直连 |
| **Bing** | ⭐⭐⭐⭐ | 桌面版极严 → **手机版UA+Patchright**突破 |
| **Google** | ⭐⭐⭐⭐⭐ | 需住宅代理 |
| **高校CMS** | 无反爬 | 直接curl |

### Bing突破实测
| 方案 | 结果 |
|:-----|:----:|
| Playwright普通 | ❌ 验证挑战 |
| curl_cffi TLS | ❌ 假结果 |
| Patchright桌面UA | ❌ 垃圾结果 |
| **Patchright+iPhone UA** | ✅ 真实结果 |

---

## ✅ 实战铁律

1. **先curl试探**，能轻不重
2. **搜索靠百度**（加满headers），被封切SM神马搜索
3. **爬取靠curl直连**（高校CMS无反爬）
4. **解析看格式**（HTML→BS4，PDF→camelot/pdftotext）
5. **每次爬完必复盘**，更新本文件
6. **删除文件用精准文件名**
7. **文件分类存放**：novels/ scrapers/ data/

---


---


---

## 🆕 2026年爬虫新趋势（GitHub探索发现）

| 趋势 | 代表项目 | ⭐ | 说明 |
|:-----|:---------|:-:|:-----|
| **MCP协议爬取** | Scrapling MCP | 71K | AI Agent通过MCP协议直接调用爬虫，无需手动编码 |
| **AI无代码爬取** | Maxun | 16.8K | 上传截图→AI自动识别元素→生成爬虫 |
| **LLM内容提取** | WebClaw | 1.9K | Rust实现，本地优先，专为LLM结构化提取设计 |
| **CDP浏览器自动化** | go-rod | 7K | 直接操作Chrome DevTools Protocol，比Playwright更底层 |
| **AI自适应选择器** | Scrapling | 71K | 网站改版自动relocate选择器，无需人工维护 |

### MCP爬取（最新趋势）
MCP (Model Context Protocol) 让AI Agent可以直接调用爬虫工具：
```python
# Scrapling MCP 示例（2026年新功能）
# pip install scrapling[mcp]
from scrapling import AsyncFetcher
from scrapling.mcp import MCPNavigator

async with AsyncFetcher() as f:
    page = await f.fetch("https://example.com")
    nav = MCPNavigator(page)
    # AI Agent 自动识别交互元素
    result = await nav.ask_ai("找到所有文章标题和链接")
```

### CDP爬取（绕过浏览器检测）
```python
# 用 go-rod 直接操作 Chrome DevTools Protocol
# 优势：比 Playwright 更底层，更难被检测
# pip install rod
from rod import Rod
browser = Rod().connect()
page = browser.page("https://example.com")
html = page.html()
```

### LLM驱动的内容提取
```python
# 传统：正则/XPath 提取
# 新方式：LLM 理解页面结构自动提取
# pip install webclaw 或 Scrapling.ai_extract()
from scrapling import AsyncFetcher

async with AsyncFetcher() as f:
    page = await f.fetch("https://example.com/article")
    # AI自动提取结构化数据
    data = await page.ai_extract({
        "title": "文章标题",
        "author": "作者名", 
        "content": "正文内容"
    })
```


# 🕷️ 爬虫工作流整合指南（完整版）

> 以下为原 `爬虫工作流指南.md` 的全部内容（30节，6421行）

# 爬虫工作流整合指南 v5（最终版）

> 整合来源：mindrally/web-scraping、lancelin111/crawl4ai-skill、asgard-ai/algo-seo-crawl、Neohu-ceo/spider-ops（spider-scaffold + spider-fix + spider-data）、apify/crawlee-python（⭐9350）+ GitHub 深挖（2026）+ 实战经验
> 
> **文档规模**：77 节 | 5850+ 行 | 185KB | 297 个子章节

---

## 一、工具选择决策树

```
目标网页 →
├─ 静态 HTML（curl 能拿到内容）→ curl + grep/sed 快速提取
├─ 需要 JS 渲染 / 有反爬（Cloudflare等）→ Playwright 无头浏览器
├─ 批量抓取（几十页以上）→ requests + BeautifulSoup + 多线程
├─ 需要搜索+爬取一体化 → crawl4ai-skill CLI
├─ 结构化大规模全站爬取 → BFS 爬虫管线（见第八节）
├─ 生产级完整项目 → spider-scaffold 模板（见第九节）
└─ 高频/强反爬/大规模 → Crawlee 框架（指纹+会话池+分层代理+自动并发，见第十一节）
```

### 快速选型速查
```
问题 1: 目标页是否需要 JS 渲染？
  NO → requests + BeautifulSoup（最快最稳）
  YES → 继续问题 2

问题 2: 有没有反爬检测？
  无/弱 → 普通 Playwright
  Cloudflare → CloakBrowser / Patchright
  DataDome/Imperva → CloakBrowser + humanize + 住宅代理
  Kasada/Akamai → CloakBrowser Pro + 字体 + 住宅代理

问题 3: 规模多大？
  < 100 页 → 脚本一次性跑
  100 ~ 10000 页 → 多线程/异步 + 代理池
  > 10000 页 → Crawlee / Scrapy + 分布式

问题 4: 是否有公开 API？
  YES → 直接调 API（最快最稳）
  NO → 按上面流程走
```

### 工具组合矩阵
| 场景 | 推荐组合 | 备选 |
|------|----------|------|
| 快速验证 | curl + grep | Jina Reader |
| 静态页批量 | requests + BS4 + ThreadPool | Scrapy |
| JS 渲染页 | Playwright | CloakBrowser |
| 强反爬 | CloakBrowser + 住宅代理 | 第三方 API |
| AI 友好输出 | Crawl4AI / trafilatura | Firecrawl |
| 大规模分布式 | Scrapy + Redis + Crawlab | Crawlee |
| 监控变化 | changedetection.io | 自建 cron |
| API 逆向 | mitmproxy + Chrome DevTools | Charles |

---

## 二、各场景实战方案

### 场景1：快速抓取单个页面信息
```bash
curl -s -L "URL" -H "User-Agent: Mozilla/5.0 ..." | grep -oP '正则'
```
- 适用：学校官网通知、搜索结果页
- 技巧：先 grep 关键词定位，再提取 href/标题

### 场景2：JS 渲染 / 反爬网站
```python
from playwright.sync_api import sync_playwright
with sync_playwright() as p:
    browser = p.chromium.launch(headless=True)
    page = browser.new_page()
    page.goto(url, wait_until="networkidle")
    content = page.content()
```
- 安装：pip3 install playwright && python3 -m playwright install chromium
- 适用：百度搜索、Cloudflare 保护的站点、雪球/知乎等动态页

### 场景3：批量抓取（章节/列表页）
```python
import requests
from bs4 import BeautifulSoup
from concurrent.futures import ThreadPoolExecutor

def fetch(url):
    r = requests.get(url, headers=headers, timeout=10)
    soup = BeautifulSoup(r.text, 'lxml')
    return soup.select('目标选择器')

with ThreadPoolExecutor(max_workers=20) as pool:
    results = list(pool.map(fetch, urls))
```

### 场景4：搜索+爬取一体化（crawl4ai-skill）
```bash
pip install crawl4ai-skill

# DuckDuckGo 搜索（免 API key，不被墙）
crawl4ai-skill search "南华大学 推免 名单"

# 单页爬取，输出精简 Markdown（去导航/侧栏/广告，省80% token）
crawl4ai-skill crawl https://example.com --format fit_markdown

# 全站爬取
crawl4ai-skill crawl-site https://docs.python.org --max-pages 50 --max-depth 3

# 搜索并爬取前N条结果
crawl4ai-skill search-and-crawl "Vue 3 best practices" --crawl-top 3

# 动态页面（等待JS渲染）
crawl4ai-skill crawl URL --wait-until networkidle --delay 2

# 等待特定元素出现
crawl4ai-skill crawl URL --wait-for ".target-selector"
```

---

## 三、最佳实践

### 请求层面
- ✅ 设置真实 User-Agent（Chrome 最新版）
- ✅ 限速：批量请求间隔 0.5~2s（反爬严格时 3~7s）
- ✅ 重试逻辑：指数退避 2^attempt + random(0,1)，最多 3 次
- ✅ 超时设置：timeout=10~30s
- ✅ 遵守 robots.txt（正式项目）
- ✅ 并行抓取时用 per-domain 信号量，避免压垮小服务器
- ✅ 429 时读 Retry-After 头，按其等待

### 解析层面
- 优先用 CSS 选择器（soup.select）或正则（grep -oP）
- 中文页面注意编码：r.encoding = r.apparent_encoding
- 处理相对路径：urljoin(base_url, href)
- 智能去噪：移除导航栏、侧边栏、广告、页脚，只保留标题+正文+代码块
- 选择器优先级：data-testid > data-* 属性 > 结构选择器 > class（避免自动生成类名）

### 反爬应对（分级）
| 级别 | 手段 | 应对 |
|------|------|------|
| L1 | 检查 UA/Headers | 补全浏览器指纹头（Sec-Fetch-*, sec-ch-ua 等） |
| L2 | 频率限制 | 加大随机延迟 3~7s |
| L3 | IP 封禁 | 代理池轮换 |
| L4 | Cloudflare/JS 验证 | 切换 Playwright + stealth.min.js |

### 数据存储
- 小量：直接输出到终端 / 写入 txt
- 中量：写入 json/csv（CSV 用 utf-8-sig 编码，Excel 兼容）
- 大量/增量：SQLite（INSERT OR REPLACE 去重）
- 分享：zip 压缩后上传 temp.sh（curl -F "file=@x.zip" https://temp.sh）

### 项目管理
```
项目结构模板：
my-spider/
├── .env                  # 敏感配置（不提交 Git）
├── .gitignore            # 忽略 .env / data/ / logs/
├── config.yaml           # 选择器配置（与代码分离）
├── main.py               # 入口
├── fetchers/             # 抓取逻辑
│   ├── static.py         # 静态页抓取
│   ├── dynamic.py        # JS 渲染抓取
│   └── api.py            # API 逆向
├── parsers/              # 解析逻辑
│   ├── list_page.py      # 列表页解析
│   └── detail_page.py    # 详情页解析
├── pipelines/            # 数据管道
│   ├── cleaner.py        # 数据清洗
│   ├── validator.py      # 数据校验
│   └── storage.py        # 数据存储
├── tests/                # 测试
│   ├── test_selectors.py # 选择器测试
│   └── snapshots/        # HTML 快照
├── data/                 # 输出数据
├── logs/                 # 运行日志
└── requirements.txt      # 依赖
```

### 开发流程
```
1. 分析目标页（DevTools 查看 DOM 结构 + Network 查看 API）
2. 写选择器测试（用快照验证）
3. 实现抓取逻辑（先单页 → 再分页 → 再全站）
4. 实现数据清洗管道
5. 添加错误处理和重试
6. 添加日志和监控
7. 测试（选择器 + 集成测试）
8. 部署（cron / Docker / 云平台）
```

### 代码规范
```python
# ✅ 好的做法：选择器配置化
SELECTORS = {
    "list_item": ".product-card",
    "title": "h2.product-name",
    "price": "span.price-current",
    "url": "a.product-link",
}

# ❌ 不好的做法：选择器硬编码散落在代码中
items = soup.select(".product-card")
for item in items:
    title = item.select_one("h2.product-name").text
    # ...
```

### 日志规范
```python
import logging

logging.basicConfig(
    level=logging.INFO,
    format="%(asctime)s [%(levelname)s] %(name)s | %(message)s",
    handlers=[
        logging.FileHandler(f"logs/spider_{datetime.now():%Y%m%d}.log"),
        logging.StreamHandler()
    ]
)
log = logging.getLogger("spider")

# 关键事件必须记录
log.info(f"开始爬取: {url}")
log.warning(f"重试: {url} (尝试 {attempt}/3)")
log.error(f"失败: {url} - {error}")
log.info(f"完成: 共 {len(items)} 条数据")
```

---

## 四、搜索引擎抓取技巧

### 搜索引擎对比
| 引擎 | 可用性 | 提取方式 | 特点 |
|------|--------|----------|------|
| DuckDuckGo | ✅ 免key不墙 | crawl4ai-skill search 或 curl HTML版 | 隐私友好，无频率限制 |
| Bing | ✅ 稳定 | curl + grep `<h2>` 或 href | 搜索质量好，支持高级语法 |
| Baidu | ⚠️ 需 Playwright | h3.t a 提取链接 | 中文搜索最好，反爬严 |
| Google | ❌ 常超时 | 不推荐直爬 | 可用 SerpAPI 等第三方 |
| Yandex | ✅ 可达 | curl HTML 版 | 俄语/独联体搜索好 |
| SearXNG | ✅ 自建 | curl API | 聚合多引擎，完全自控 |

### 各引擎实战命令

**DuckDuckGo HTML 版（最推荐）：**
```bash
# 直接 curl，不需要 JS
curl -s "https://html.duckduckgo.com/html/?q=南华大学+推免+名单+2026" \
  -H "User-Agent: Mozilla/5.0 ..." | \
  grep -oP 'href="(https?://[^"]*)"[^>]*>[^<]*' | head -20
```

**Bing 搜索（稳定）：**
```bash
curl -s "https://www.bing.com/search?q=site:usc.edu.cn+推免+名单" \
  -H "User-Agent: Mozilla/5.0 ..." | \
  grep -oP '<h2><a[^>]*href="(https?://[^"]*)"[^>]*>[^<]*</a></h2>' | head -20
```

**百度（需 Playwright）：**
```python
from playwright.sync_api import sync_playwright
with sync_playwright() as p:
    browser = p.chromium.launch(headless=True)
    page = browser.new_page()
    page.goto("https://www.baidu.com/s?wd=南华大学+推免+名单")
    links = page.eval_on_selector_all("h3.t a", "els => els.map(e => ({title: e.textContent, href: e.href}))")
    browser.close()
```

**SearXNG 自建搜索引擎（聚合多引擎）：**
```bash
docker run -d -p 8888:8080 searxng/searxng
# 访问 http://localhost:8888 搜索
# API: curl "http://localhost:8888/search?q=xxx&format=json"
```

### 搜索高级语法
```
site:domain.com          # 限定域名
"精确短语"               # 精确匹配
filetype:pdf             # 限定文件类型
intitle:推免             # 标题包含
inurl:notice             # URL 包含
after:2025-09-01         # 日期之后（Google/Bing）
-排除词                  # 排除某词
```

### 搜索 + 爬取组合技
```bash
# 1. 先搜索找到目标页面
# 2. 再用 curl 深入爬取具体页面
# 3. 如果搜索不到，尝试 sitemap/RSS/直接猜测路径
```

---

## 五、中国大学官网爬取经验

### 基础规律
- 官网一般静态 HTML，curl 即可
- 通知/公告页路径常见：/tzgg.htm、/xwzx/tzgg.htm、/notice/
- 分页：/tzgg/1.htm、/tzgg/2.htm 或 list-1.html
- 文章页路径：/info/1988/8767.htm 这种格式
- 教务处(jwc)、研究生院(yjs)、招生网(zs/zsw) 是保研/推免信息的主要来源
- 编码多为 UTF-8，少数 GBK 需处理

### 常见 CMS 系统路径模式
| CMS | 特征路径 | 编码 |
|-----|----------|------|
| 博达（博达CMS） | /site/default/article/xxx | UTF-8 |
| 方正（Founder） | /news/xxx/xxx.htm | GBK/UTF-8 |
| 希尔（Hill） | /html/xxx/xxx.html | UTF-8 |
| 青果（Kingosoft） | /xww/xxx/xxx.htm | GBK |
| 天波（Tianbo） | /a/xxx/xxx.html | GBK |
| WCM（TRS） | /col/xxx/xxx.shtml | GBK |

### 编码处理
```python
# 老站常见 GBK 编码问题
resp = requests.get(url)
# 方法1：自动检测
resp.encoding = resp.apparent_encoding
# 方法2：强制指定
resp.encoding = 'gbk'
# 方法3：从 HTML meta 标签推断
import re
match = re.search(rb'charset=["\']?([^"\'\s;>]+)', resp.content[:2048])
if match:
    resp.encoding = match.group(1).decode()
```

### 找信息的技巧
```
1. 先搜 sitemap.xml / robots.txt
2. 从首页导航找"通知公告"/"招生就业"入口
3. 用 site: 搜索限定范围
4. 保研/推免通常 9~10 月发布，公示期 7~15 天
5. 过期下架的内容：
   - web.archive.org 历史快照
   - Google cache:URL
   - 各学院官网可能有二级公示
6. 附件（.doc/.xls/.pdf）可能需要校内 IP
```

### 批量爬取多学院
```python
# 南华大学各学院官网通常是独立子域或子路径
colleges = [
    "https://cs.usc.edu.cn",      # 计算机学院
    "https://med.usc.edu.cn",     # 医学院
    "https://ee.usc.edu.cn",      # 电气工程学院
    # ...
]
for college_url in colleges:
    # 找通知公告列表 → 搜索"推免"/"保研" → 提取文章链接
    pass
```

---

## 六、环境依赖速查

### 基础环境
```bash
# Python 基础 + 解析库
apt-get update && apt-get install -y python3-pip
pip3 install requests beautifulsoup4 lxml urllib3 chardet --break-system-packages
```

### JS 渲染
```bash
# Playwright（无头浏览器）
pip3 install playwright --break-system-packages
python3 -m playwright install chromium
```

### 反检测浏览器
```bash
# CloakBrowser（Chromium 源码级隐身）
pip3 install cloakbrowser --break-system-packages

# Camoufox（Firefox 源码级隐身）
pip3 install camoufox --break-system-packages

# Patchright（Playwright 去自动化标记）
pip3 install patchright --break-system-packages

# Botright（指纹 + AI 验证码）
pip3 install botright --break-system-packages
```

### 生产级框架
```bash
# Crawlee（指纹+会话池+代理+自动并发）
pip3 install 'crawlee[all]' --break-system-packages
playwright install

# Scrapling（自适应选择器 + 隐身）
pip3 install scrapling --break-system-packages

# Scrapy（大规模结构化爬取）
pip3 install scrapy scrapy-redis --break-system-packages
```

### 搜索 + 一体化
```bash
# crawl4ai-skill CLI
pip3 install crawl4ai-skill --break-system-packages

# Crawl4AI 库
pip3 install crawl4ai --break-system-packages
crawl4ai-setup
```

### TLS 指纹伪装
```bash
# tls-client（Python）
pip3 install tls-client --break-system-packages

# browserforge（指纹生成）
pip3 install browserforge --break-system-packages
```

### 辅助工具
```bash
# 正文提取
pip3 install trafilatura newspaper3k --break-system-packages

# PDF 处理
pip3 install pymupdf --break-system-packages

# 表格处理
pip3 install pandas openpyxl --break-system-packages

# OCR
pip3 install paddleocr paddlepaddle --break-system-packages

# 抓包
pip3 install mitmproxy --break-system-packages

# 页面监控
docker run -d -p 5000:5000 -v ./data:/datastore dgtlmoon/changedetection.io
```

### Linux 字体包（反爬必需）
```bash
# 安装字体（Canvas 指纹 + emoji 渲染需要）
sudo apt install -y \
    fonts-noto-color-emoji \
    fonts-freefont-ttf \
    fonts-unifont \
    fonts-ipafont-gothic \
    fonts-wqy-zenhei \
    fonts-tlwg-loma-otf
```

### 一键安装脚本
```bash
# 完整开发环境（约 5 分钟）
apt-get update && apt-get install -y \
    python3-pip xvfb \
    fonts-noto-color-emoji fonts-freefont-ttf fonts-unifont \
    fonts-ipafont-gothic fonts-wqy-zenhei fonts-tlwg-loma-otf && \
pip3 install --break-system-packages \
    requests beautifulsoup4 lxml chardet \
    playwright tls-client browserforge \
    scrapling crawl4ai trafilatura \
    feedparser pandas pymupdf paddleocr && \
python3 -m playwright install chromium
```

---

## 七、数据清洗入库管线（spider-data）

```
Raw HTML/JSON → parse_extract → clean_normalize → validate → deduplicate → store
```

### 安全提取
```python
def extract_text(el, selector, default=""):
    target = el.select_one(selector) if selector else el
    return target.get_text(strip=True) if target else default

def extract_num(text):
    """'1,234.56元' → 1234.56"""
    cleaned = re.sub(r"[^\d.]", "", text.replace(",", ""))
    try: return float(cleaned)
    except ValueError: return None
```

### 清洗器
```python
CLEANERS = {
    "text": lambda s: s.strip() if s else "",
    "url": lambda s: s.strip().split("?")[0] if s else "",  # 去追踪参数
    "price": lambda s: float(re.sub(r"[^\d.]", "", str(s))) if s else 0.0,
    "unicode": lambda s: unicodedata.normalize("NFKC", s) if s else "",
    "whitespace": lambda s: re.sub(r"\s+", " ", s).strip() if s else "",
}
```

### 去重
```python
def deduplicate(records, key_fields=None):
    if key_fields is None:
        seen, unique = set(), []
        for r in records:
            h = hash(json.dumps(r, sort_keys=True, default=str))
            if h not in seen: seen.add(h); unique.append(r)
        return unique
    seen, unique = set(), []
    for r in records:
        key = tuple(str(r.get(f, "")) for f in key_fields)
        if key not in seen: seen.add(key); unique.append(r)
    return unique
```

### 编码检测
```python
import chardet
def read_with_encoding(raw_bytes):
    detected = chardet.detect(raw_bytes)
    return raw_bytes.decode(detected["encoding"] or "utf-8", errors="replace")
```

### JSON 扁平化（嵌套→CSV友好）
```python
def flatten_json(nested, parent_key="", sep="_"):
    items = {}
    for k, v in nested.items():
        new_key = f"{parent_key}{sep}{k}" if parent_key else k
        if isinstance(v, dict): items.update(flatten_json(v, new_key, sep))
        elif isinstance(v, list): items[new_key] = json.dumps(v, ensure_ascii=False)
        else: items[new_key] = v
    return items
```

---

## 八、全站爬虫管线（BFS 算法，algo-seo-crawl）

适用于：站点审计、批量采集某站所有页面、SEO 分析

### 流程
```
1. 输入验证：解析种子URL → 获取 robots.txt → 确定爬取范围（同域/子域）
2. 核心循环：
   - 初始化 URL 队列（BFS 用 FIFO，优先级用堆）
   - 出队 → 检查：未访问 + robots允许 + 范围内
   - 抓取（带超时+重试+限速）
   - 解析 HTML：提取链接（标准化+去重）+ 提取内容
   - 新链接入队，存储数据
   - 重复直到队列空或达到上限
3. 验证：无 robots 违规、无重复页、所有 URL 有交代
4. 输出：站点地图 + 链接图 + 元数据
```

### 关键陷阱防范
| 陷阱 | 应对 |
|------|------|
| URL 不统一（大小写/尾斜杠/默认端口） | 标准化：小写host、去默认端口、去尾/、排序query参数 |
| 日历页/无限分页/session ID | 设 max_depth + URL 模式限制 |
| 重定向循环 | 最多跟随 5 次重定向后停止 |
| 软404（返回200但内容是错误页） | 检测页面内容特征，不能只看状态码 |
| 编码不统一 | 从 HTTP header + meta 标签检测，回退 charset 库 |

### Python 实现（BFS 全站爬虫）
```python
import requests, time, random, json, logging
from urllib.parse import urlparse, urljoin, urlunparse
from urllib.robotparser import RobotFileParser
from bs4 import BeautifulSoup
from collections import deque
from pybloom_live import BloomFilter

log = logging.getLogger("bfs_crawler")

class BFSCrawler:
    def __init__(self, seed_url, max_pages=1000, max_depth=3, delay=1.0):
        self.seed_url = seed_url
        self.max_pages = max_pages
        self.max_depth = max_depth
        self.delay = delay
        self.domain = urlparse(seed_url).netloc
        
        # BFS 队列：(url, depth)
        self.queue = deque([(seed_url, 0)])
        # Bloom Filter 去重（内存高效，10M 容量）
        self.visited = BloomFilter(capacity=10_000_000, error_rate=0.001)
        # 结果存储
        self.results = []
        # robots.txt 解析器
        self.rp = RobotFileParser()
        self.rp.set_url(f"https://{self.domain}/robots.txt")
        try:
            self.rp.read()
        except:
            log.warning("Failed to read robots.txt, proceeding without it")
        
        self.session = requests.Session()
        self.session.headers.update({
            "User-Agent": "Mozilla/5.0 (compatible; MyCrawler/1.0)"
        })
    
    def normalize_url(self, url):
        """URL 标准化"""
        parsed = urlparse(url)
        # 小写 host，去默认端口，去 fragment
        normalized = urlunparse((
            parsed.scheme.lower(),
            parsed.netloc.lower().split(':')[0],  # 去端口
            parsed.path.rstrip('/') or '/',         # 统一尾斜杠
            parsed.params,
            parsed.query,  # 保留 query（排序在下面）
            ''  # 去 fragment
        ))
        return normalized
    
    def is_allowed(self, url):
        """检查 robots.txt 是否允许"""
        try:
            return self.rp.can_fetch("*", url)
        except:
            return True
    
    def is_same_domain(self, url):
        """检查是否同域"""
        return urlparse(url).netloc == self.domain
    
    def fetch(self, url):
        """抓取页面（带重试）"""
        for attempt in range(3):
            try:
                resp = self.session.get(url, timeout=30, allow_redirects=True)
                if len(resp.history) > 5:  # 重定向循环检测
                    return None
                if resp.status_code == 200:
                    return resp
                if resp.status_code == 429:
                    wait = int(resp.headers.get("Retry-After", 60))
                    time.sleep(wait)
                    continue
                return None
            except requests.RequestException:
                time.sleep(2 ** attempt)
        return None
    
    def extract_links(self, soup, base_url):
        """提取并标准化所有链接"""
        links = set()
        for a in soup.find_all("a", href=True):
            href = a["href"]
            full_url = urljoin(base_url, href)
            normalized = self.normalize_url(full_url)
            if self.is_same_domain(normalized):
                links.add(normalized)
        return links
    
    def extract_content(self, soup, url):
        """提取页面内容"""
        title = soup.title.string.strip() if soup.title else ""
        meta_desc = ""
        meta = soup.find("meta", attrs={"name": "description"})
        if meta:
            meta_desc = meta.get("content", "")
        # 正文提取（简化版）
        for tag in soup(["script", "style", "nav", "footer", "header", "aside"]):
            tag.decompose()
        body_text = soup.get_text(separator=" ", strip=True)[:2000]
        
        return {
            "url": url,
            "title": title,
            "description": meta_desc,
            "text_preview": body_text[:500],
        }
    
    def run(self):
        """执行 BFS 爬取"""
        log.info(f"Starting BFS crawl from {self.seed_url}")
        log.info(f"Max pages: {self.max_pages}, Max depth: {self.max_depth}")
        
        while self.queue and len(self.results) < self.max_pages:
            url, depth = self.queue.popleft()
            
            # 跳过：已访问、超出深度、robots 禁止
            if url in self.visited:
                continue
            if depth > self.max_depth:
                continue
            if not self.is_allowed(url):
                log.info(f"Blocked by robots.txt: {url}")
                continue
            
            self.visited.add(url)
            
            # 抓取
            resp = self.fetch(url)
            if not resp:
                continue
            
            # 解析
            soup = BeautifulSoup(resp.text, "lxml")
            
            # 提取内容
            content = self.extract_content(soup, url)
            content["depth"] = depth
            content["links_found"] = 0
            self.results.append(content)
            
            # 提取链接并入队
            if depth < self.max_depth:
                links = self.extract_links(soup, url)
                content["links_found"] = len(links)
                for link in links:
                    if link not in self.visited:
                        self.queue.append((link, depth + 1))
            
            log.info(f"[{len(self.results)}/{self.max_pages}] depth={depth} {url}")
            time.sleep(self.delay + random.uniform(0, 0.5))
        
        log.info(f"Crawl complete: {len(self.results)} pages")
        return self.results
    
    def save(self, output_path):
        """保存结果"""
        with open(output_path, "w", encoding="utf-8") as f:
            json.dump({
                "seed_url": self.seed_url,
                "total_pages": len(self.results),
                "pages": self.results,
            }, f, ensure_ascii=False, indent=2)
        log.info(f"Saved to {output_path}")

# 使用
crawler = BFSCrawler("https://example.com", max_pages=500, max_depth=3)
results = crawler.run()
crawler.save("crawl_results.json")
```

### 软 404 检测
```python
def is_soft_404(resp, soup):
    """检测软 404（状态码 200 但实际是错误页）"""
    text = soup.get_text(strip=True).lower()
    # 常见 404 关键词
    indicators = [
        "page not found", "404", "not found",
        "页面不存在", "找不到页面", "sorry",
        "no results found", "this page doesn't exist"
    ]
    # 页面文字太少也可能是软 404
    if len(text) < 100:
        return True
    # 检查关键词
    for indicator in indicators:
        if indicator in text:
            return True
    return False
```

---

## 九、生产级爬虫模板（spider-scaffold）

### 静态模板（Requests + BS4）
```python
#!/usr/bin/env python3
import time, random, logging, json, csv, requests
from datetime import datetime
from pathlib import Path
from bs4 import BeautifulSoup

BASE_URL = "https://target.com"
OUTPUT_DIR = Path("data"); OUTPUT_DIR.mkdir(exist_ok=True)

logging.basicConfig(level=logging.INFO, format="%(asctime)s [%(levelname)s] %(message)s",
    handlers=[logging.FileHandler(f"logs/spider_{datetime.now():%Y%m%d}.log"), logging.StreamHandler()])
log = logging.getLogger("spider")

session = requests.Session()
session.headers.update({
    "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36",
    "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
    "Accept-Language": "en-US,en;q=0.9,zh-CN;q=0.8,zh;q=0.7",
    "DNT": "1", "Connection": "keep-alive", "Upgrade-Insecure-Requests": "1",
})

def fetch(url, max_retries=3):
    for attempt in range(max_retries):
        try:
            resp = session.get(url, timeout=30)
            if resp.status_code == 429:
                wait = int(resp.headers.get("Retry-After", 60))
                log.warning(f"429 rate-limited. Waiting {wait}s...")
                time.sleep(wait); continue
            resp.raise_for_status()
            return resp
        except requests.RequestException as e:
            log.warning(f"Attempt {attempt+1}/{max_retries} failed: {e}")
            if attempt < max_retries - 1:
                time.sleep(2 ** attempt + random.uniform(0, 1))
    raise RuntimeError(f"Failed after {max_retries} retries: {url}")

def parse(response):
    soup = BeautifulSoup(response.text, "html.parser")
    items = []
    for el in soup.select("CSS_SELECTOR"):
        items.append({
            "title": el.select_one(".title").get_text(strip=True) if el.select_one(".title") else "",
            "url": el.select_one("a")["href"] if el.select_one("a") else "",
            "scraped_at": datetime.now().isoformat(),
        })
    return items

def save(items, fmt="json"):
    ts = datetime.now().strftime("%Y%m%d_%H%M%S")
    if fmt == "json":
        path = OUTPUT_DIR / f"data_{ts}.json"
        path.write_text(json.dumps(items, ensure_ascii=False, indent=2))
    elif fmt == "csv":
        path = OUTPUT_DIR / f"data_{ts}.csv"
        if items:
            with open(path, "w", newline="", encoding="utf-8-sig") as f:
                writer = csv.DictWriter(f, fieldnames=items[0].keys())
                writer.writeheader(); writer.writerows(items)
    log.info(f"Saved {len(items)} records → {path}")

def main():
    log.info("Spider started — %s", BASE_URL)
    items = []
    # 分页循环
    for page in range(1, MAX_PAGES + 1):
        resp = fetch(f"{BASE_URL}?page={page}")
        batch = parse(resp)
        if not batch: break
        items.extend(batch)
        time.sleep(random.uniform(2, 5))
    save(items)
    log.info("Done — %d total records", len(items))

if __name__ == "__main__":
    main()
```

### JS 模板（Playwright）
```python
#!/usr/bin/env python3
import time, random, logging, json
from datetime import datetime
from pathlib import Path
from playwright.sync_api import sync_playwright

BASE_URL = "https://target.com"
OUTPUT_DIR = Path("data"); OUTPUT_DIR.mkdir(exist_ok=True)
logging.basicConfig(level=logging.INFO, format="%(asctime)s [%(levelname)s] %(message)s")
log = logging.getLogger("spider")

def main():
    with sync_playwright() as p:
        browser = p.chromium.launch(headless=True)
        context = browser.new_context(
            user_agent="Mozilla/5.0 ... Chrome/131.0.0.0 Safari/537.36",
            viewport={"width": 1920, "height": 1080},
        )
        page = context.new_page()
        page.set_default_timeout(30000)
        page.goto(BASE_URL, wait_until="networkidle")

        # 滚动加载懒内容
        for _ in range(3):
            page.evaluate("window.scrollBy(0, document.body.scrollHeight)")
            time.sleep(1)

        items = page.evaluate("""() => {
            return Array.from(document.querySelectorAll('.item')).map(el => ({
                title: el.querySelector('.title')?.innerText?.trim() || '',
                url: el.querySelector('a')?.href || '',
            }));
        }""")

        ts = datetime.now().strftime("%Y%m%d_%H%M%S")
        path = OUTPUT_DIR / f"data_{ts}.json"
        path.write_text(json.dumps(items, ensure_ascii=False, indent=2))
        log.info("Saved %d records → %s", len(items), path)
        browser.close()

if __name__ == "__main__":
    main()
```

### 搭建规则
1. **先看目标页源码**（curl 或 WebFetch），从真实 DOM 生成选择器
2. **默认加分页**：列表页实现 ?page=N 或 "下一页" 按钮循环
3. **必须包含**：随机延迟 2~5s、浏览器头、指数退避重试、错误日志、时间戳文件名
4. **自动检测列表/详情模式**：如果是列表页，爬列表→详情页
5. **代理池**（高频时）：random.choice(PROXY_POOL) 轮换

---

## 十、爬虫自修复协议（spider-fix）

当爬虫出错时，按此流程诊断修复：

```
1. RUN    → 执行爬虫，捕获完整 stderr/stdout
2. DIAG   → 分类错误（5 类之一）
3. FETCH  → 拉取目标页面最新 HTML
4. REWRITE → 应用对应修复模板
5. TEST   → 连续跑 2 次，都通过 = 修好
6. RECORD → 记录修复案例到知识库
```

### 5 类错误及修复

| # | 错误类型 | 症状 | 修复 |
|---|----------|------|------|
| 1 | 选择器漂移 | 无报错但 items=[] | 重新检查 DOM，优先用 data-* 属性 |
| 2 | 403 反爬 | HTTPError 403 / Cloudflare 页 | L1补头→L2加延迟→L3代理→L4 Playwright |
| 3 | 429 限频 | 429 Too Many Requests | 读 Retry-After，加大基础延迟 |
| 4 | API 结构变化 | KeyError / TypeError | 用 .get() + 多字段回退 + schema 版本检测 |
| 5 | 依赖缺失 | ModuleNotFoundError | pip install 对应包 |

### 选择器修复优先级
```
1. data-testid / data-* 属性（最稳定）
2. 结构性选择器（div.product-list > div[class*='card']）
3. XPath text() 锚定（//a[contains(text(),'更多')]）
4. ❌ 避免：自动生成的 class（如 .css-1a2b3c）
```

### 自动诊断框架
```python
import subprocess, json, logging
from datetime import datetime
from pathlib import Path
from bs4 import BeautifulSoup

log = logging.getLogger("spider-fix")
KB_PATH = Path("knowledge_base.jsonl")  # 修复知识库

class SpiderDoctor:
    """爬虫自修复诊断器"""
    
    def diagnose(self, spider_script: str) -> dict:
        """运行爬虫并诊断错误类型"""
        # 1. 执行爬虫，捕获输出
        result = subprocess.run(
            ["python3", spider_script],
            capture_output=True, text=True, timeout=120
        )
        
        stderr = result.stderr.lower()
        stdout = result.stdout
        
        # 2. 分类错误
        if "module not found" in stderr or "importerror" in stderr:
            return {"type": "dependency", "detail": stderr}
        
        if "403" in stderr or "forbidden" in stderr:
            return {"type": "anti_bot_403", "detail": stderr}
        
        if "429" in stderr or "too many requests" in stderr:
            return {"type": "rate_limit_429", "detail": stderr}
        
        if "keyerror" in stderr or "typeerror" in stderr:
            return {"type": "api_change", "detail": stderr}
        
        # 检查是否返回空数据（选择器漂移）
        if '"items": []' in stdout or '"count": 0' in stdout:
            return {"type": "selector_drift", "detail": "Empty results"}
        
        if result.returncode == 0:
            return {"type": "success", "detail": "OK"}
        
        return {"type": "unknown", "detail": stderr}
    
    def fix_selector_drift(self, target_url: str, old_selector: str) -> str:
        """尝试修复选择器漂移"""
        import requests
        resp = requests.get(target_url, headers={
            "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7)"
        })
        soup = BeautifulSoup(resp.text, "lxml")
        
        # 策略1：尝试 data-testid 属性
        for el in soup.find_all(attrs={"data-testid": True}):
            test_id = el.get("data-testid")
            new_selector = f'[data-testid="{test_id}"]'
            # 检查是否能找到多个元素（列表项通常 > 1）
            if len(soup.select(new_selector)) >= 2:
                log.info(f"修复: {old_selector} → {new_selector}")
                return new_selector
        
        # 策略2：尝试 aria-label
        for el in soup.find_all(attrs={"aria-label": True}):
            label = el.get("aria-label")
            new_selector = f'[aria-label="{label}"]'
            if len(soup.select(new_selector)) >= 2:
                log.info(f"修复: {old_selector} → {new_selector}")
                return new_selector
        
        # 策略3：尝试结构性选择器
        # 找包含重复子元素的容器
        for container in soup.find_all(["div", "ul", "section"]):
            children = list(container.children)
            if len(children) >= 3:
                # 检查子元素结构是否相似
                tags = [c.name for c in children if hasattr(c, 'name') and c.name]
                if len(set(tags)) == 1 and len(tags) >= 3:
                    tag = tags[0]
                    parent_tag = container.name
                    parent_class = container.get("class", [""])[0] if container.get("class") else ""
                    if parent_class:
                        new_selector = f"{parent_tag}.{parent_class} > {tag}"
                    else:
                        new_selector = f"{parent_tag} > {tag}"
                    if len(soup.select(new_selector)) >= 3:
                        log.info(f"修复: {old_selector} → {new_selector}")
                        return new_selector
        
        return None  # 无法自动修复
    
    def fix_rate_limit(self, current_delay: float) -> float:
        """修复限频：加倍延迟"""
        new_delay = current_delay * 2
        log.info(f"修复: delay {current_delay}s → {new_delay}s")
        return new_delay
    
    def fix_anti_bot(self, level: int) -> dict:
        """升级反爬方案"""
        upgrades = {
            1: {"action": "add_headers", "detail": "补全 Sec-Fetch-* 头"},
            2: {"action": "add_delay", "detail": "增加随机延迟 3~7s"},
            3: {"action": "add_proxy", "detail": "启用代理池"},
            4: {"action": "switch_playwright", "detail": "切换到 Playwright"},
            5: {"action": "switch_cloakbrowser", "detail": "切换到 CloakBrowser"},
        }
        return upgrades.get(level, {"action": "give_up", "detail": "使用第三方 API"})
    
    def record_fix(self, diagnosis: dict, fix: str, target_domain: str):
        """记录修复案例到知识库"""
        entry = {
            "timestamp": datetime.now().isoformat(),
            "domain": target_domain,
            "error_type": diagnosis["type"],
            "fix": fix,
            "detail": diagnosis.get("detail", "")[:200],
        }
        with open(KB_PATH, "a") as f:
            f.write(json.dumps(entry, ensure_ascii=False) + "\n")
        log.info(f"记录修复: {target_domain} [{diagnosis['type']}] → {fix}")

# 使用示例
doctor = SpiderDoctor()
diag = doctor.diagnose("my_spider.py")
if diag["type"] == "selector_drift":
    new_sel = doctor.fix_selector_drift("https://target.com", ".product-v2")
    if new_sel:
        doctor.record_fix(diag, new_sel, "target.com")
```

### 知识库查询
```python
def query_kb(domain: str = None, error_type: str = None):
    """查询修复知识库"""
    results = []
    with open(KB_PATH) as f:
        for line in f:
            entry = json.loads(line)
            if domain and domain not in entry.get("domain", ""):
                continue
            if error_type and entry.get("error_type") != error_type:
                continue
            results.append(entry)
    return results

# 查看某域名的历史修复记录
history = query_kb(domain="example.com")
for h in history:
    print(f"{h['timestamp']} | {h['error_type']} → {h['fix']}")
```

---

## 十一、高级反检测架构（来自 Crawlee ⭐9350）

Crawlee 是 Apify 开源的生产级爬虫框架，其反检测设计值得学习：

### 浏览器指纹生成（browserforge）
```python
# pip install browserforge
from browserforge.fingerprints import FingerprintGenerator
from browserforge.headers import HeaderGenerator

# 生成完整浏览器指纹（UA + 屏幕 + 插件 + WebGL 等）
fp_gen = FingerprintGenerator(browser='chrome')
fingerprint = fp_gen.generate()

# 生成配套 HTTP 头（sec-ch-ua, Accept-Language 等自动匹配）
hdr_gen = HeaderGenerator(browser='chrome')
headers = hdr_gen.generate()
```
**核心思想**：UA 和 sec-ch-ua 必须匹配同一浏览器版本，否则被检测为机器人。

### 会话池管理（Session Pool）
```python
# 每个 Session 模拟一个独立用户
class Session:
    max_age = timedelta(minutes=50)      # 会话最长存活时间
    max_usage_count = 50                 # 最多使用次数
    max_error_score = 3.0               # 错误分达到此值 → 标记为被封
    error_score_decrement = 0.5          # 每次成功请求减少的错误分
    blocked_status_codes = [401, 403, 429]  # 触发封禁的状态码
```
**策略**：
- 池大小默认 1000，随机抽取会话使用
- 收到 403/429 → 错误分 +1，达到阈值 → 废弃该会话
- 成功请求 → 错误分 -0.5（渐进恢复）
- 会话过期或用完 → 自动创建新会话

### 代理分层轮换（Tiered Proxy）
```python
# 分层代理：优先用便宜/快的，被封后升级到更贵的
proxy_config = ProxyConfiguration(
    tiered_proxy_urls=[
        ["http://cheap-proxy1:8080", "http://cheap-proxy2:8080"],  # Tier 0
        ["http://premium-proxy1:8080"],                             # Tier 1（被封后升级）
    ]
)
# 同一 session_id 绑定同一代理出口 IP（保持会话一致性）
```

### 自动并发调节（Autoscaled Pool）
```
每 10s 检测一次系统负载（CPU + 内存）：
- 负载低 → 并发 +5%（scale up）
- 负载高 → 并发 -5%（scale down）
- 必须达到当前并发的 90% 利用率才允许继续扩容
```
**意义**：不会因为开太多线程把本机或目标服务器搞崩。

### HTTP 客户端选择
| 客户端 | 特点 | 适用 |
|--------|------|------|
| ImpitHttpClient（默认） | 模拟浏览器 TLS 指纹 | 一般场景 |
| CurlImpersonateHttpClient | curl-impersonate，模拟 Chrome/Firefox TLS | 需要绕过 TLS 指纹检测 |
| HttpxHttpClient | 标准 HTTP/2 | 简单 API 调用 |
| Playwright | 完整浏览器 | JS 渲染 / Cloudflare |

### 状态持久化（断点续爬）
- URL 队列、会话池、统计数据均可持久化到磁盘
- 爬虫崩溃后重启，从上次位置继续，不重复爬取
- 适合大规模任务（几万页以上）

---

## 十二、反检测浏览器生态（2025-2026 前沿）

### 检测原理（知己知彼）

反爬系统检测维度：
```
1. JS 注入检测：Object.getOwnPropertyDescriptor 发现被覆写的属性
2. 自动化标记：navigator.webdriver=true、CDP 变量泄露
3. 指纹一致性：UA 与 sec-ch-ua 不匹配、Windows UA + Apple GPU
4. TLS 指纹（JA3/JA4）：握手特征与声称的浏览器不符
5. 行为分析：鼠标轨迹、点击间隔、滚动模式
6. 市场份额异常：Linux 流量突然从 5% 涨到 20% → 全部弹验证码
```

### 反检测工具对比

| 工具 | ⭐ | 原理 | 适用场景 |
|------|-----|------|----------|
| **CloakBrowser** | 28.9K | 源码级 C++ 补丁（71处），修改 canvas/WebGL/audio/GPU/WebRTC | 最强隐身，Playwright 直接替换 |
| **Camoufox** | 10.4K | Firefox 源码级修改，Juggler 协议隔离，BrowserForge 指纹 | Firefox 生态，AI Agent 多会话 |
| **Patchright** | 3.9K | Playwright 驱动层补丁，去除自动化标记 | Playwright 用户无缝切换 |
| **undetected-chromedriver** | 12.8K | Selenium ChromeDriver 补丁 | Selenium 老项目 |
| **Botright** | 1.0K | Playwright + 指纹伪装 + AI 验证码解决 | 需要过验证码的场景 |

### CloakBrowser 用法（Playwright 一行替换）
```python
# pip install cloakbrowser
from cloakbrowser import launch

# 基础用法（替代 playwright.sync_api）
browser = launch()
page = browser.new_page()
page.goto("https://target.com")
browser.close()

# 强反爬场景
browser = launch(
    proxy="http://user:pass@residential-proxy:port",  # 住宅代理
    geoip=True,       # 时区+语言自动匹配代理 IP
    headless=False,    # 某些站检测 headless
    humanize=True,     # 人类化鼠标/键盘/滚动
)
```

### Camoufox 核心思想
- Playwright 代码在**隔离沙箱**中运行，页面完全看不到注入的 JS
- 输入通过 Firefox 原生事件处理器发送，与真人操作一致
- 指纹按**真实市场份额分布**生成（Linux 5%、Windows 70%、Mac 25%）
- 关键：指纹必须**内部一致**（不能 Windows UA + Apple GPU）

### Patchright（Playwright 无缝替换）
```python
# pip install patchright
# 把 from playwright.sync_api import ... 改成：
from patchright.sync_api import sync_playwright
# 其余代码完全不变
```

---

## 十三、TLS 指纹伪装

### 什么是 TLS 指纹检测？
服务器在 TLS 握手阶段就能识别客户端类型（JA3/JA4 哈希）。Python requests 的 TLS 指纹与浏览器完全不同，即使 UA 伪装了也会被识别。

### TLS 握手指纹原理
```
客户端 → 服务器 (ClientHello)：
├─ TLS 版本（1.2 / 1.3）
├─ 密码套件列表（顺序很重要！）
│   Chrome: [1301, 1302, 1303, 49195, 49199, ...]
│   Python: [49200, 49199, 49201, ...]  ← 完全不同
├─ 扩展列表（SNI, ALPN, supported_versions, ...）
├─ 扩展顺序
├─ ALPN 协议（h2, http/1.1）
├─ 椭圆曲线列表
└─ 签名算法列表

以上组合的 MD5 哈希 = JA3 指纹
每个浏览器/客户端的 JA3 是固定的，服务器据此判断是否为爬虫
```

### JA3 指纹对比
| 客户端 | JA3 哈希 |
|--------|----------|
| Chrome 120 | cd08e31494f9531f560d64c695473da9 |
| Firefox 121 | 839bbe3ed680eb42e9f7e8497c9825f7 |
| Python requests | 3b5074b1b5d032e5620f69f9f700ff0e |
| curl | 163665f02c75e00d5b4e8b4e6b8a8e6a |

→ Python/curl 的指纹一看就不是浏览器

### 解决方案

| 工具 | ⭐ | 说明 |
|------|-----|------|
| **curl-impersonate** | 6.6K | 特制 curl，模拟 Chrome/Firefox/Safari 的 TLS 握手 |
| **Python-Tls-Client** | 817 | Python 封装，`pip install tls-client`，模拟各浏览器 TLS |
| **Impit**（Crawlee 默认） | — | Rust 实现，模拟浏览器 TLS 指纹的 HTTP 客户端 |
| **curl_cffi** | — | Python 的 curl-impersonate 绑定，`pip install curl_cffi` |

### tls-client 详细用法
```python
import tls_client

# 创建会话（模拟特定浏览器）
session = tls_client.Session(
    client_identifier="chrome_131",  # 支持: chrome_103~131, firefox_102~133, safari_15_5~17_0
    random_tls_extension_order=True,  # 随机化扩展顺序（更难被检测）
)

# 像 requests 一样使用
resp = session.get("https://target.com", headers={
    "User-Agent": "Mozilla/5.0 ... Chrome/131.0.0.0 Safari/537.36",
    "Accept": "text/html,application/xhtml+xml,...",
})
print(resp.status_code, resp.text[:200])

# POST 请求
resp = session.post("https://api.target.com/data", json={
    "query": "products",
    "page": 1,
})

# 带 Cookie
resp = session.get("https://target.com/dashboard", headers={
    "Cookie": "session_id=abc123; token=xyz789",
})
```

### curl_cffi 用法（推荐）
```python
from curl_cffi import requests

# 模拟 Chrome 指纹
resp = requests.get(
    "https://target.com",
    impersonate="chrome131",  # 支持: chrome, firefox, safari, edge
)

# 带代理
resp = requests.get(
    "https://target.com",
    impersonate="chrome131",
    proxies={"https": "http://proxy:8080"},
)

# 会话复用
session = requests.Session(impersonate="chrome131")
resp1 = session.get("https://target.com/page1")
resp2 = session.get("https://target.com/page2")
# Cookie 自动保持
```

### curl-impersonate 命令行
```bash
# 安装（需要编译）
# https://github.com/lwthiker/curl-impersonate

# 模拟 Chrome
curl_chrome131 https://target.com

# 模拟 Firefox
curl_ff133 https://target.com

# 模拟 Safari
curl_safari17_0 https://target.com

# 带代理
curl_chrome131 -x http://proxy:8080 https://target.com

# 验证 JA3 指纹
curl_chrome131 https://ja3er.com/json
# 返回的 ja3_hash 应该和真 Chrome 一致
```

### 验证你的 TLS 指纹是否伪装成功
```bash
# 方法1：ja3er.com（在线检测）
curl_chrome131 https://ja3er.com/json
# 应返回 {"ja3_hash": "cd08e31494f9531f560d64c695473da9"} (Chrome 131 的哈希)

# 方法2：httpbin（查看请求头）
curl_chrome131 https://httpbin.org/headers

# 方法3：Cloudflare 检测页
curl_chrome131 https://nowsecure.nl
# 如果能通过说明 TLS 指纹没问题
```

### 检测层级与对应方案
```
L0: 无检测 → curl / requests 直接上
L1: UA/Header 检查 → 补全浏览器头
L2: TLS 指纹（JA3）→ curl-impersonate / tls-client / Impit / curl_cffi
L3: JS 环境检测 → Playwright / Patchright
L4: 浏览器指纹（canvas/WebGL/audio）→ CloakBrowser / Camoufox
L5: 行为分析 → humanize=True / 人类化鼠标算法
L6: 验证码（reCAPTCHA/Turnstile）→ Botright AI 解题 / 第三方打码
```

---

## 十四、AI 驱动爬取（新趋势）

| 工具 | ⭐ | 特点 |
|------|-----|------|
| **Firecrawl** | 154K | API 服务，搜索+爬取+交互，输出 LLM 友好的 Markdown |
| **Crawl4AI** | 74K | 开源 LLM 友好爬虫，Docker 部署，浏览器池+MCP 集成+实时监控 |
| **browser-use** | 106K | AI Agent 浏览器自动化，自然语言驱动操作网页 |
| **ScrapeGraphAI** | — | 用自然语言描述要爬什么，LLM 自动生成爬虫逻辑 |
| **Jina Reader** | — | `https://r.jina.ai/URL` 直接返回页面 Markdown（免费） |
| **crawl4ai-skill** | — | 本地 CLI，fit_markdown 去噪省 80% token |
| **webclaw** | 1.9K | Rust 实现，本地优先，CLI + REST API + MCP server |

### Jina Reader（零配置，最简）
```bash
# 任何 URL 前加 r.jina.ai/ 即可获取 Markdown
curl "https://r.jina.ai/https://target.com"
# 返回干净的 Markdown，自动去导航/广告/侧栏
```

### Crawl4AI（开源，Docker 部署）
```bash
pip install crawl4ai && crawl4ai-setup
# 或 Docker
docker pull unclecode/crawl4ai:latest
docker run -d -p 11235:11235 --shm-size=1g unclecode/crawl4ai:latest
# 监控面板: http://localhost:11235/dashboard
# 试验场: http://localhost:11235/playground
```

### Firecrawl（API 服务）
```python
# pip install firecrawl-py
from firecrawl import FirecrawlApp
app = FirecrawlApp(api_key="your-key")
result = app.scrape_url("https://target.com", params={"formats": ["markdown"]})
print(result["markdown"])
```

---

## 十五、自适应爬取框架（Scrapling ⭐70.7K）

Scrapling 的核心创新：**选择器自动修复**。网站改版后，无需手动更新选择器。

### 核心用法
```python
from scrapling.fetchers import StealthyFetcher

# 首次爬取：保存元素特征
StealthyFetcher.adaptive = True
page = StealthyFetcher.fetch('https://example.com', headless=True, network_idle=True)
products = page.css('.product-card', auto_save=True)  # 保存元素位置/文本/属性特征

# 网站改版后：自动重新定位
products = page.css('.product-card', adaptive=True)  # 即使 class 变了也能找到
```

### 三种 Fetcher
| Fetcher | 用途 |
|---------|------|
| `Fetcher` / `AsyncFetcher` | 纯 HTTP，最快 |
| `StealthyFetcher` | 隐身浏览器，过 Cloudflare Turnstile |
| `DynamicFetcher` | JS 渲染，等待动态内容 |

### Spider 框架（大规模爬取）
```python
from scrapling.spiders import Spider, Response

class MySpider(Spider):
    name = "demo"
    start_urls = ["https://example.com/"]

    async def parse(self, response: Response):
        for item in response.css('.product'):
            yield {"title": item.css('h2::text').get()}
        # 自动跟踪分页、代理轮换、暂停/恢复

MySpider().start()
```

---

## 十六、WAF/反爬分级绕过实战

### 4 层递进策略（来自 Trawl ⭐445）
```
请求进入
  │
  ▼
Tier 1: 纯 HTTP 请求 ────── 成功 ──→ 返回（< 100ms）
  │ 被拦截
  ▼
Tier 2: 缓存的浏览器会话 ── 成功 ──→ 返回（~500ms）
  │ 缓存过期/未命中
  ▼
Tier 3: 新建浏览器解题 ──── 成功 ──→ 缓存 + 返回（4~15s）
  │ IP 被标记
  ▼
Tier 4: 住宅代理 + 浏览器 ─ 成功 ──→ 缓存 + 返回（15~45s）
  │ 失败
  ▼
  报错
```
**核心思想**：不要一上来就用最重的方案，逐层升级，能快则快。

### 各 WAF 系统应对

| WAF/反爬 | 检测特点 | 绕过方案 |
|----------|----------|----------|
| **Cloudflare Turnstile** | JS 挑战 + 浏览器环境检测 | CloakBrowser / Patchright / Trawl |
| **Cloudflare IUAM** | 5s 等待 + JS 计算 | 等待 + 真实浏览器 |
| **DataDome** | 行为分析 + 设备指纹 | headed 模式 + humanize + 住宅代理 |
| **Kasada** | Canvas emoji 哈希 + 深度指纹 | 安装完整字体 + CloakBrowser Pro |
| **Akamai/Imperva** | TLS 指纹 + JS 混淆 | curl-impersonate + 字体包 |
| **PerimeterX (HUMAN)** | 行为生物特征 | 人类化鼠标/键盘 + 住宅 IP |
| **reCAPTCHA v3** | 行为评分（0~1） | Botright（AI 解题，得分 0.9） |
| **hCaptcha / GeeTest** | 图像/滑块验证 | 第三方打码 API / Trawl 内置解决 |

### CloakBrowser 实战排错

**被 FingerprintJS 检测？**
```python
browser = launch(
    headless=False,
    proxy="http://user:pass@residential-proxy:port",
    geoip=True,
    args=[
        "--fingerprint-noise=false",          # 防止噪声注入被 ML 检测
        "--fingerprint-windows-font-metrics", # 字体度量对齐（需 148+ 版本）
    ],
)
```

**Kasada/Akamai 仍被拦截？** → 大概率是 Linux 缺字体：
```bash
sudo apt install -y fonts-noto-color-emoji fonts-freefont-ttf fonts-unifont \
    fonts-ipafont-gothic fonts-wqy-zenhei fonts-tlwg-loma-otf
```

**首次访问被挑战，第二次正常？** → 用持久化 profile 预热 cookie：
```python
from cloakbrowser import launch_persistent_context
# 首次：预热
ctx = launch_persistent_context("./profile", args=["--disable-http2"])
page = ctx.new_page()
page.goto("https://target.com")  # 拿到 cookie
ctx.close()
# 后续：直接复用
ctx = launch_persistent_context("./profile")
```

**DataDome 仍拦截？** → headed + 虚拟显示器：
```bash
sudo apt install xvfb
Xvfb :99 -screen 0 1920x1080x24 &
export DISPLAY=:99
```
```python
browser = launch(headless=False, proxy="http://residential:port", humanize=True)
```

### 代理选择指南
| 类型 | 价格 | 适用 |
|------|------|------|
| 数据中心代理 | 最便宜 | 无 IP 信誉检测的站 |
| 住宅代理（轮换） | $0.5~5/GB | 大多数反爬站 |
| 住宅代理（粘性） | 稍贵 | 需要保持会话的场景 |
| 移动代理（4G/5G） | 最贵 | 最严格的 IP 信誉检测 |
| ISP 代理（静态住宅） | $5/IP/月 | 长期稳定访问同一站 |

**关键原则**：
- 数据中心 IP 会被 IP 信誉库直接标记，大多数反爬站必须用住宅代理
- `geoip=True`：时区+语言必须匹配代理出口 IP 的地理位置
- SOCKS5 优于 HTTP CONNECT（避免 HTTP/2 兼容问题）

---

## 十七、行为伪装（人类化操作）

反爬系统不仅看指纹，还分析**行为模式**：鼠标轨迹、点击间隔、滚动速度、打字节奏。

### 鼠标轨迹（贝塞尔曲线）
```python
# CloakBrowser 内置：humanize=True 即可
browser = launch(humanize=True)

# 或独立使用 cloakbrowser-human 包
# pip install cloakbrowser-human
# 原理：用贝塞尔曲线生成自然鼠标轨迹，而非直线移动
# 包含：距离感知轨迹、随机微抖、加减速曲线
```

### 打字模拟
```python
# 真人打字特征：
# - 每个键间隔 50~200ms（非固定）
# - 偶尔停顿 300~800ms（思考）
# - 偶尔打错再退格
import random, time

def human_type(page, selector, text):
    page.click(selector)
    for char in text:
        page.keyboard.type(char)
        time.sleep(random.uniform(0.05, 0.2))
        if random.random() < 0.03:  # 3% 概率打错
            page.keyboard.press("Backspace")
            time.sleep(0.1)
            page.keyboard.type(char)
```

### 滚动模拟
```python
# 真人滚动：非匀速，有停顿，偶尔回滚
def human_scroll(page, total_distance=3000):
    scrolled = 0
    while scrolled < total_distance:
        step = random.randint(200, 500)
        page.mouse.wheel(0, step)
        scrolled += step
        time.sleep(random.uniform(0.3, 1.2))
        if random.random() < 0.1:  # 10% 概率回滚
            page.mouse.wheel(0, -random.randint(50, 150))
            time.sleep(0.5)
```

### 页面停留与交互
- 进入页面后等 1~3s 再操作（模拟阅读）
- 随机移动鼠标到页面不同区域
- 点击前鼠标先移到目标附近（非瞬移）
- 表单填写顺序：从上到下，偶尔跳回修改

---

## 十八、API 逆向与抓包

当网页爬取太复杂时，直接调用底层 API 往往更高效。

### 工具链
| 工具 | ⭐ | 用途 |
|------|-----|------|
| **mitmproxy** | 44K | TLS 中间人代理，拦截/修改/重放 HTTP(S) 请求 |
| Chrome DevTools | — | Network 面板，查看 XHR/Fetch 请求 |
| Charles Proxy | — | GUI 抓包工具（付费） |
| Fiddler | — | Windows 抓包（免费） |
| HttpCanary | — | Android 抓包 APP |

### 逆向流程
```
1. 打开 DevTools → Network → 筛选 XHR/Fetch
2. 操作页面（翻页/搜索/加载），观察请求
3. 找到数据接口：通常是 /api/v1/xxx 或 /graphql
4. 分析请求参数：query string / POST body / headers
5. 检查鉴权：Cookie / Token / 签名（sign/timestamp/nonce）
6. 用 curl/requests 复现请求
7. 如果有签名 → 分析 JS 源码找签名算法 → 用 Python 复现
```

### 常见签名破解
```python
# 典型签名结构：sign = md5(params_sorted + timestamp + secret_key)
import hashlib, time

def make_sign(params: dict, secret: str) -> str:
    sorted_str = "&".join(f"{k}={v}" for k, v in sorted(params.items()))
    raw = f"{sorted_str}&timestamp={int(time.time())}&key={secret}"
    return hashlib.md5(raw.encode()).hexdigest()
```

### 注意事项
- 优先找**移动端 API**（通常比 Web 端鉴权简单）
- GraphQL 接口可以用 introspection 查询获取完整 schema
- 注意请求频率，API 通常有更严格的限流
- 有些 API 返回 protobuf 而非 JSON，需要对应解码

---

## 十九、页面监控与增量爬取

| 工具 | ⭐ | 用途 |
|------|-----|------|
| **changedetection.io** | 32K | 自托管网页变化监控，支持通知（Telegram/邮件/Webhook） |
| Scrapling adaptive | — | 选择器自动修复，网站改版不用改代码 |
| Crawlee 状态持久化 | — | 断点续爬，不重复抓取 |

### changedetection.io（Docker 一键部署）
```bash
docker run -d -p 5000:5000 -v ./data:/datastore dgtlmoon/changedetection.io
# 访问 http://localhost:5000 添加要监控的 URL
# 支持：内容变化检测、价格监控、补货提醒、JS 渲染页面
```

### 增量爬取策略
```python
# 方案1：基于 Last-Modified / ETag
headers = {"If-Modified-Since": last_modified}
resp = session.get(url, headers=headers)
if resp.status_code == 304:
    pass  # 未变化，跳过

# 方案2：基于内容哈希
import hashlib
content_hash = hashlib.md5(resp.content).hexdigest()
if content_hash == stored_hash:
    pass  # 未变化

# 方案3：基于时间戳字段（API）
params = {"updated_after": last_crawl_time}
```

---

## 二十、分布式爬虫与代理池

### 分布式架构（Scrapy-Redis）
```
                    ┌─────────────┐
                    │  Redis 队列  │ ← URL 去重 + 任务分发
                    └──────┬──────┘
           ┌───────────────┼───────────────┐
           ▼               ▼               ▼
     ┌──────────┐   ┌──────────┐   ┌──────────┐
     │ Spider 1 │   │ Spider 2 │   │ Spider 3 │  ← 多机/多进程
     └──────────┘   └──────────┘   └──────────┘
           │               │               │
           └───────────────┼───────────────┘
                           ▼
                    ┌─────────────┐
                    │  数据存储    │ ← MySQL / MongoDB / ES
                    └─────────────┘
```bash
pip install scrapy-redis
# 关键配置：
# SCHEDULER = "scrapy_redis.scheduler.Scheduler"
# DUPEFILTER_CLASS = "scrapy_redis.dupefilter.RFPDupeFilter"
# REDIS_URL = "redis://localhost:6379"
```

### 代理池管理（haipproxy ⭐5.5K）
```
代理池生命周期：
抓取免费代理 → 验证可用性 → 存入 Redis（按速度/匿名度评分）
→ 爬虫请求时随机取用 → 失败则标记降分 → 定期清理死代理
```

### 分布式管理面板
| 工具 | ⭐ | 说明 |
|------|-----|------|
| **Gerapy** | 3.5K | Scrapy + Scrapyd + Django + Vue 分布式管理 |
| **Crawlab** | — | 支持 Scrapy/BS4/Selenium 的爬虫管理平台 |
| **SpiderFlow** | — | 可视化爬虫设计 + 分布式执行 |

---

## 二十一、JS 逆向与反混淆

当网站用 JS 加密参数/签名时，需要逆向其逻辑。

### 工具链
| 工具 | 用途 |
|------|------|
| Chrome DevTools → Sources | 断点调试、查看调用栈 |
| **jsdeob-workbench**（⭐118） | 可视化 AST 变换，链式反混淆 |
| **AST Explorer**（astexplorer.net） | 在线查看/编辑 JS AST |
| **Babel** | JS 编译器，用于 AST 变换还原代码 |
| **Node.js** | 本地执行还原后的 JS 获取签名 |
| **PyExecJS / js2py** | Python 中执行 JS 代码 |

### 常见混淆手段与还原
| 混淆 | 特征 | 还原方法 |
|------|------|----------|
| 字符串数组 | `_0x1a2b[0x3]` | 找到数组，用 AST 替换为明文 |
| 控制流平坦化 | `switch(状态机)` | 按执行顺序重排 case |
| 死代码注入 | 大量无用 if/else | AST 删除不可达分支 |
| 变量名混淆 | `_0x3f2a1b` | 根据上下文重命名 |
| 编码加密 | `atob()` / `charCodeAt` | 直接执行解码函数 |

### 逆向流程
```
1. Network 面板找到带签名的请求
2. 全局搜索签名参数名（如 "sign"、"token"、"_signature"）
3. 在 Sources 面板打断点，观察调用栈
4. 找到加密函数 → 分析逻辑
5. 如果代码被混淆 → 用 AST 工具还原
6. 用 Node.js 或 Python 复现加密逻辑
7. 集成到爬虫中
```

### 快速方案：直接执行 JS
```python
# 如果加密逻辑不复杂，直接用 Node 执行
import subprocess, json

def get_sign(params):
    js_code = f"""
    const crypto = require('./site_crypto.js');  // 从网站提取的加密模块
    console.log(JSON.stringify(crypto.sign({json.dumps(params)})));
    """
    result = subprocess.run(['node', '-e', js_code], capture_output=True, text=True)
    return json.loads(result.stdout)
```

---

## 二十二、正文提取算法（去噪）

从 HTML 中精准提取正文内容，去除导航/广告/侧栏/页脚。

### 工具对比
| 工具 | ⭐ | 特点 |
|------|-----|------|
| **trafilatura** | 3K+ | Python，学术级精度，支持 XML/JSON/Markdown 输出 |
| **readability-lxml** | — | Mozilla Readability 的 Python 移植 |
| **newspaper3k** | 14K | 新闻文章专用，自动提取标题/作者/日期/正文 |
| **goose3** | — | 类似 newspaper，支持多语言 |
| **Jina Reader** | — | 在线 API，零配置 |
| **Crawl4AI fit_markdown** | — | 内置去噪，省 80% token |

### trafilatura 用法
```python
# pip install trafilatura
import trafilatura

# 从 URL 直接提取
result = trafilatura.fetch_url("https://target.com/article")
text = trafilatura.extract(result, output_format="markdown")

# 从本地 HTML 提取
with open("page.html") as f:
    html = f.read()
text = trafilatura.extract(html, include_comments=False, include_tables=True)
```

### 正文提取核心算法原理
```
1. 文本密度法：计算每个 DOM 节点的 文字长度/标签数 比值
   - 正文区域：文字多、标签少（<p> 密集）
   - 导航/侧栏：标签多、文字少（<a>/<li> 密集）

2. 块级标签评分：
   - <p>, <article>, <main> → 加分
   - <nav>, <aside>, <footer>, <header> → 减分
   - class/id 含 "content", "article", "post" → 加分
   - class/id 含 "sidebar", "nav", "ad", "comment" → 减分

3. 连续文本块合并：相邻的高分 <p> 合并为正文
```

---

## 二十三、性能优化

### 异步并发（aiohttp / httpx）
```python
import asyncio, httpx

async def fetch_all(urls, max_concurrent=20):
    semaphore = asyncio.Semaphore(max_concurrent)
    async with httpx.AsyncClient(timeout=30) as client:
        async def fetch(url):
            async with semaphore:
                resp = await client.get(url)
                await asyncio.sleep(0.5)  # 限速
                return resp.text
        return await asyncio.gather(*[fetch(u) for u in urls])

results = asyncio.run(fetch_all(url_list))
```

### 连接复用与池化
```python
# requests Session 自动复用 TCP 连接
session = requests.Session()
adapter = requests.adapters.HTTPAdapter(
    pool_connections=20,   # 连接池大小
    pool_maxsize=20,       # 每个 host 最大连接数
    max_retries=3,         # 自动重试
)
session.mount("https://", adapter)
session.mount("http://", adapter)
```

### DNS 缓存
```bash
# 本地 DNS 缓存，避免重复解析
# Linux: systemd-resolved 或 dnsmasq
# 或在 /etc/hosts 中固定常用域名 IP
```

### 性能基准参考
| 方案 | 吞吐量（页/秒） | 适用 |
|------|-----------------|------|
| curl 单线程 | 1~3 | 调试 |
| requests + 20 线程 | 10~30 | 静态页批量 |
| aiohttp + 50 并发 | 30~80 | 静态页高吞吐 |
| Scrapy 默认 | 20~50 | 结构化爬取 |
| Crawlee 自动并发 | 自适应 | 生产级 |
| Playwright 单实例 | 0.5~2 | JS 渲染 |
| Playwright 5 实例 | 3~8 | JS 渲染批量 |

---

## 二十四、指纹检测向量详解（知道对手在看什么）

### 浏览器指纹采集点
```
┌─ 网络层 ─────────────────────────────────────────┐
│ • TLS 握手特征（JA3/JA4 哈希）                    │
│ • HTTP/2 SETTINGS 帧顺序和参数（Akamai FP）       │
│ • TCP 窗口大小 / TTL                              │
│ • DNS 解析时间                                    │
└──────────────────────────────────────────────────┘
┌─ HTTP 头 ────────────────────────────────────────┐
│ • User-Agent 与 sec-ch-ua 版本一致性              │
│ • Accept-Language 与 IP 地理位置匹配              │
│ • 头的顺序（Chrome/Firefox/Safari 各不相同）      │
│ • 缺失的头（如 DNT、Upgrade-Insecure-Requests）   │
└──────────────────────────────────────────────────┘
┌─ JS 环境 ────────────────────────────────────────┐
│ • navigator.webdriver === true?                   │
│ • window.chrome 对象是否存在                      │
│ • Permissions API 行为（Notification 权限）       │
│ • iframe contentWindow 检测                      │
│ • toString() 是否返回 [native code]              │
│ • Object.getOwnPropertyDescriptor 检测覆写       │
└──────────────────────────────────────────────────┘
┌─ 渲染指纹 ───────────────────────────────────────┐
│ • Canvas 2D：绘制文字+图形 → 像素哈希            │
│ • WebGL：renderer/vendor 字符串 + 渲染哈希       │
│ • AudioContext：音频处理浮点差异                  │
│ • 字体枚举：测量文字宽度推断已安装字体            │
│ • Emoji 渲染：不同 OS 渲染结果不同（Kasada 用）   │
│ • 屏幕分辨率 vs 窗口大小 vs devicePixelRatio     │
└──────────────────────────────────────────────────┘
┌─ 行为特征 ───────────────────────────────────────┐
│ • 鼠标移动轨迹（直线 vs 贝塞尔曲线）             │
│ • 点击坐标分布（总是中心 vs 随机偏移）           │
│ • 按键间隔（固定 vs 正态分布）                    │
│ • 滚动模式（匀速 vs 加速减速）                    │
│ • 页面停留时间 / 交互深度                         │
│ • 事件时间戳精度（performance.now 分辨率）        │
└──────────────────────────────────────────────────┘
┌─ 环境一致性 ─────────────────────────────────────┐
│ • UA 声称 Windows 但 GPU 是 Apple M1 → 矛盾     │
│ • 时区 UTC 但 IP 在北京 → 矛盾                   │
│ • 屏幕 1920x1080 但 viewport 也是 1920x1080      │
│   （真人有书签栏/标签栏，viewport < screen）      │
│ • Linux 流量占比异常（5% → 20%）→ 群体异常       │
└──────────────────────────────────────────────────┘
```

### 对应防御清单
| 检测向量 | 防御工具/方法 |
|----------|--------------|
| TLS 指纹 | tls-client / curl-impersonate / Impit |
| HTTP/2 指纹 | CloakBrowser（源码级修改 SETTINGS 帧） |
| 头顺序/一致性 | browserforge 生成配套头 |
| JS 环境检测 | Patchright / Camoufox（隔离注入） |
| Canvas/WebGL | CloakBrowser C++ 补丁 / Camoufox 源码修改 |
| 字体枚举 | 安装 Windows 字体 + --fingerprint-windows-font-metrics |
| 行为分析 | humanize=True / 贝塞尔曲线鼠标 / 随机延迟 |
| 环境一致性 | geoip=True（时区+语言匹配 IP） |
| 屏幕/viewport | 设置 viewport < screen（留出工具栏空间） |

---

## 二十五、常见坑与反模式

### ❌ 不要这样做
```python
# 1. 固定延迟（容易被统计检测）
time.sleep(2)  # ❌ 每次恰好 2s → 机器人特征
time.sleep(random.uniform(1.5, 4.5))  # ✅ 随机范围

# 2. 完美顺序访问（1,2,3,4...页）
for i in range(1, 100):  # ❌ 顺序爬 → 明显爬虫
random.shuffle(pages)     # ✅ 随机顺序

# 3. 忽略 robots.txt 和 ToS
# ❌ 可能面临法律风险（特别是欧美）
# ✅ 至少检查 robots.txt，尊重 Crawl-delay

# 4. 单 IP 高并发
ThreadPoolExecutor(max_workers=50)  # ❌ 同一 IP 50 并发 → 秒封
# ✅ 控制并发 + 代理轮换

# 5. 不处理重定向和相对路径
url = el.select_one("a")["href"]  # ❌ 可能是 /path 或 ../path
url = urljoin(base_url, href)     # ✅

# 6. 假设页面结构永远不变
soup.select(".product-v2")  # ❌ 网站改版就挂
# ✅ 用 Scrapling adaptive 或多选择器回退

# 7. 忽略编码
resp.text  # ❌ 可能是乱码
resp.encoding = resp.apparent_encoding  # ✅
```

### 蜜罐陷阱识别
```
• display:none 的链接 → 不要爬（真人看不到）
• robots.txt 中 Disallow 的路径 → 不要爬（可能是陷阱）
• 页面中隐藏的文字 "如果你是爬虫请点击这里" → 忽略
• 连续快速访问后返回 200 但内容是验证页 → 检测内容而非状态码
```

---

## 二十六、Cookie 与会话管理策略

### Cookie 持久化
```python
# requests：保存/加载 cookie
import pickle
# 保存
with open("cookies.pkl", "wb") as f:
    pickle.dump(session.cookies, f)
# 加载
with open("cookies.pkl", "rb") as f:
    session.cookies = pickle.load(f)
```

### 会话轮换策略
```
场景1：无登录 → 每 N 个请求换一次 Session（清 cookie）
场景2：需登录 → 维护多个账号 Session 池，轮换使用
场景3：有验证码 → 验证码后 cookie 有效期短，需频繁刷新
场景4：Cloudflare → cf_clearance cookie 有效 30min，过期需重新解题
```

### Cookie 预热（针对首次访问挑战）
```python
# 某些站首次访问会下挑战 cookie，第二次才放行
# 方案：用持久化 profile 预热一次，后续复用
from cloakbrowser import launch_persistent_context
ctx = launch_persistent_context("./warm_profile")
page = ctx.new_page()
page.goto("https://target.com")  # 第一次：拿 cookie
ctx.close()
# 后续所有请求复用 ./warm_profile 中的 cookie
```

---

## 二十七、调度与编排

### 轻量级：cron + 脚本
```bash
# 每天凌晨 3 点爬一次
0 3 * * * cd /opt/spider && python3 main.py >> /var/log/spider.log 2>&1
```

### 中量级：Celery + Redis
```python
from celery import Celery
app = Celery('spider', broker='redis://localhost:6379')

@app.task(bind=True, max_retries=3)
def crawl_page(self, url):
    try:
        return fetch_and_parse(url)
    except Exception as e:
        self.retry(exc=e, countdown=60)
```

### 重量级：Airflow / Prefect
```python
# Airflow DAG 示例：每日增量爬取
from airflow import DAG
from airflow.operators.python import PythonOperator
from datetime import datetime, timedelta

dag = DAG("daily_spider", schedule_interval="0 3 * * *",
          default_args={"retries": 3, "retry_delay": timedelta(minutes=5)})

crawl = PythonOperator(task_id="crawl", python_callable=run_spider, dag=dag)
clean = PythonOperator(task_id="clean", python_callable=clean_data, dag=dag)
load  = PythonOperator(task_id="load", python_callable=load_to_db, dag=dag)
crawl >> clean >> load
```

### 爬虫管理平台
| 平台 | ⭐ | 特点 |
|------|-----|------|
| **Crawlab** | 12K | 支持任何语言/框架，可视化调度+日志+告警 |
| **Gerapy** | 3.5K | Scrapy 专用，Django + Vue 管理面板 |
| **SpiderFlow** | — | 可视化拖拽设计爬虫流程 |
| **Feapder** | — | 国产框架，内置去重/增量/报警 |

---

## 二十八、特殊场景爬取策略

### 电商网站
```
• 价格/库存：通常在 JSON API 中（/api/product/xxx），不必解析 HTML
• 搜索接口：?q=关键词&page=N&sort=price，注意反爬限流
• 详情页：懒加载图片用 data-src 而非 src
• 评论：异步加载，找 /api/reviews?product_id=xxx
• 注意：价格可能有会员价/地区差异，需带对应 cookie
```

### 社交媒体
```
• Twitter/X：用 Nitter 实例或 syndication API，避免直接爬
• Instagram：graphql API（i.instagram.com/graphql/query/）
• TikTok：webapp API + X-Bogus 签名（需逆向）
• 小红书：shield 签名 + 设备指纹
• 通用建议：优先找移动端 API（鉴权通常更简单）
```

### 新闻/内容站
```
• RSS 优先：大多数新闻站有 /rss 或 /feed
• sitemap.xml：获取所有文章 URL
• 正文提取：trafilatura / newspaper3k 一步到位
• 付费墙：检查是否有 ?outputType=amp 或 archive.org 缓存
• 日期范围：site:domain.com after:2025-01-01（搜索引擎语法）
```

### 政府/教育网站（如南华大学）
```
• 编码：可能是 GBK/GB2312，注意 resp.encoding
• 结构：老式表格布局，用 soup.find_all('tr') 遍历
• 分页：?page=2 或 ?p=2 或 JavaScript 跳转
• 附件：.doc/.xls/.pdf 链接，需额外下载
• 公示期：推免/保研名单通常公示 7~15 天后下架
• 时间窗口：每年 9~10 月发布，错过需等下一年或找缓存
```

---

## 二十九、数据质量保障

### 爬取后校验清单
```python
def validate_crawl(items, rules):
    """爬取完成后自动校验"""
    issues = []
    
    # 1. 数量检查
    if len(items) == 0:
        issues.append("❌ 零结果：选择器可能失效或被反爬")
    if len(items) < expected_min:
        issues.append(f"⚠️ 结果偏少：{len(items)} < 预期 {expected_min}")
    
    # 2. 字段完整性
    for i, item in enumerate(items):
        for field in required_fields:
            if not item.get(field):
                issues.append(f"❌ 第{i}条缺 {field}")
    
    # 3. 重复检查
    unique = len(set(json.dumps(x, sort_keys=True) for x in items))
    if unique < len(items) * 0.9:
        issues.append(f"⚠️ 重复率过高：{(1-unique/len(items))*100:.1f}%")
    
    # 4. 编码检查
    for item in items[:10]:
        for v in item.values():
            if isinstance(v, str) and '\ufffd' in v:
                issues.append("❌ 编码错误：存在替换字符 U+FFFD")
                break
    
    # 5. URL 有效性
    for item in items[:5]:
        if item.get("url") and not item["url"].startswith("http"):
            issues.append(f"⚠️ URL 格式异常：{item['url']}")
    
    return issues
```

### 异常检测
```
• 突然全部返回相同内容 → 被反爬拦截（返回验证页）
• 数据量突然为 0 → 选择器漂移或网站改版
• 响应时间突然变长 → 可能被限流
• 状态码全是 200 但内容一样 → 软封禁
```

---

## 三十、HTTP/2 指纹与高级检测

### Akamai HTTP/2 指纹
除了 TLS（JA3），HTTP/2 连接本身也有指纹：
```
检测点：
• SETTINGS 帧参数顺序和值（HEADER_TABLE_SIZE, MAX_CONCURRENT_STREAMS 等）
• WINDOW_UPDATE 值
• PRIORITY 帧权重
• 头的压缩方式（HPACK 动态表使用模式）

Chrome 和 Python httpx 的 HTTP/2 指纹完全不同。
```

**应对**：
- CloakBrowser 源码级修改了 SETTINGS 帧
- curl-impersonate 模拟了完整 HTTP/2 指纹
- 如果不需要 HTTP/2，用 `--disable-http2` 降级到 HTTP/1.1 绕过

### WebRTC 泄露
```
即使走了代理，WebRTC 可能泄露真实 IP。
防御：
• CloakBrowser/Camoufox 已在源码级禁用 WebRTC
• Playwright: 启动参数 --disable-features=WebRTC
• 或配置代理为 SOCKS5（覆盖 UDP）
```

### Canvas 指纹噪声
```
原理：在 Canvas 渲染结果中注入微小随机噪声，使每次哈希不同。
风险：高级检测（FingerprintJS ML）能识别"噪声模式"→ 判定为篡改。
CloakBrowser 方案：
  --fingerprint-noise=false  # 关闭噪声，用确定性指纹替代
  配合 --fingerprint=42069   # 固定种子 → 每次生成相同但真实的指纹
```

---

## 三十一、表格/PDF/图片爬取

### HTML 表格 → 结构化数据
```python
import pandas as pd
# 直接读取页面中所有表格
dfs = pd.read_html("https://target.com/page-with-tables")
# 或从已获取的 HTML
dfs = pd.read_html(resp.text)
# dfs[0] 就是第一个表格的 DataFrame
```

### PDF 内容提取
```python
# pip install pymupdf (fitz)
import fitz
doc = fitz.open("downloaded.pdf")
for page in doc:
    text = page.get_text()
    tables = page.find_tables()  # 提取表格
```

### 图片批量下载
```python
import requests
from pathlib import Path
from concurrent.futures import ThreadPoolExecutor

def download_img(url, save_dir="images"):
    Path(save_dir).mkdir(exist_ok=True)
    name = url.split("/")[-1].split("?")[0]
    resp = requests.get(url, timeout=30)
    (Path(save_dir) / name).write_bytes(resp.content)

# 从页面提取所有图片 URL
imgs = [img["src"] for img in soup.select("img[src]")]
imgs = [urljoin(base_url, src) for src in imgs]

with ThreadPoolExecutor(max_workers=10) as pool:
    list(pool.map(download_img, imgs))
```

### 懒加载图片
```python
# 很多站用 data-src / data-original 代替 src
imgs = soup.select("img[data-src]")
urls = [img["data-src"] for img in imgs]
# 或 Playwright 滚动后再提取
```

---

## 三十二、限流检测与自适应调速

### 识别被限流的信号
```python
def detect_throttling(resp, history):
    """检测是否被限流"""
    signals = []
    
    # 1. 状态码
    if resp.status_code == 429:
        signals.append("429 Too Many Requests")
    if resp.status_code == 503:
        signals.append("503 Service Unavailable（可能过载保护）")
    
    # 2. 响应头
    if "Retry-After" in resp.headers:
        signals.append(f"Retry-After: {resp.headers['Retry-After']}s")
    if "X-RateLimit-Remaining" in resp.headers:
        remaining = int(resp.headers["X-RateLimit-Remaining"])
        if remaining < 10:
            signals.append(f"配额即将耗尽: {remaining}")
    
    # 3. 响应时间突增
    if history and resp.elapsed.total_seconds() > sum(history) / len(history) * 3:
        signals.append("响应时间异常增长（可能被人工降速）")
    
    # 4. 内容变化
    if "captcha" in resp.text.lower() or "verify" in resp.text.lower():
        signals.append("出现验证码/验证页面")
    
    return signals
```

### 自适应调速算法（AIMD）
```python
# Additive Increase / Multiplicative Decrease（类似 TCP 拥塞控制）
class AdaptiveRate:
    def __init__(self, initial_delay=1.0, min_delay=0.3, max_delay=30.0):
        self.delay = initial_delay
        self.min_delay = min_delay
        self.max_delay = max_delay
    
    def on_success(self):
        # 成功：线性增加速度（减少延迟）
        self.delay = max(self.min_delay, self.delay - 0.1)
    
    def on_throttle(self):
        # 被限流：指数增加延迟
        self.delay = min(self.max_delay, self.delay * 2)
    
    def on_block(self):
        # 被封禁：直接拉到最大延迟
        self.delay = self.max_delay

rate = AdaptiveRate()
# 使用：time.sleep(rate.delay + random.uniform(0, 0.5))
```

---

## 三十三、中国平台特殊处理

### 微信公众号
```
• 文章链接：mp.weixin.qq.com/s/xxx
• 反爬：需要 Referer + Cookie，频率限制严格
• 方案：搜狗微信搜索（weixin.sogou.com）间接获取
• 或用 RSS 服务（WeRSS、feeddd）
```

### 知乎
```
• API：www.zhihu.com/api/v4/xxx
• 反爬：x-zse-96 签名（需逆向 JS）
• 方案：用浏览器 cookie 直接调 API，或 Playwright 渲染
```

### 哔哩哔哩
```
• API：api.bilibili.com/x/xxx（大部分公开）
• 视频信息：/x/web-interface/view?bvid=xxx
• 评论：/x/v2/reply?type=1&oid=xxx&pn=1
• 反爬较轻：带 UA 即可，注意频率
```

### 淘宝/天猫
```
• 反爬极重：滑块验证 + 设备指纹 + 行为分析
• 方案：官方开放平台 API（需商家授权）
• 或：移动端 H5 页面（m.taobao.com）相对宽松
```

### 百度
```
• 搜索结果：需 Playwright（curl 拿到的是验证页）
• 贴吧/知道：静态 HTML 可 curl，但频率限制严
• 百度网盘：需登录 cookie + sign 签名
```

---

## 三十四、反爬对抗升级路线图

当目标站升级反爬时，按此路线逐步升级：

```
阶段 0：无防护
  └─ curl / requests 直接爬

阶段 1：基础检测（UA/频率）
  └─ 补全浏览器头 + 随机延迟 + 限速

阶段 2：IP 信誉/频率限制
  └─ 代理池轮换 + 自适应调速（AIMD）

阶段 3：TLS 指纹检测（JA3/JA4）
  └─ tls-client / curl-impersonate / Impit

阶段 4：JS 环境检测（navigator.webdriver 等）
  └─ Patchright / undetected-chromedriver

阶段 5：浏览器指纹（Canvas/WebGL/Audio/Font）
  └─ CloakBrowser / Camoufox（源码级修改）

阶段 6：行为分析（鼠标/键盘/滚动）
  └─ humanize=True + 贝塞尔曲线 + 随机打字

阶段 7：验证码（reCAPTCHA/Turnstile/hCaptcha）
  └─ Botright AI / 2captcha API / Trawl 内置

阶段 8：设备指纹 + 环境一致性深度检测
  └─ 住宅代理 + geoip + Windows 字体 + 持久化 profile

阶段 9：企业级（Kasada/Akamai/PerimeterX 全套）
  └─ CloakBrowser Pro + 住宅代理 + headed + 字体 + humanize
     或直接用第三方 API（HyperSolutions/RiskBypass）
```

**原则：能用低阶段方案解决的，绝不用高阶段。**

---

## 三十五、法律与合规要点

### 中国法律红线
```
• 《刑法》285条：非法获取计算机信息系统数据罪
  → 绕过技术保护措施获取数据，情节严重可判刑
• 《数据安全法》：重要数据出境需评估
• 《个人信息保护法》：爬取个人信息需合法基础
• 反不正当竞争法：大量爬取竞品数据可能构成不正当竞争
```

### 安全操作建议
```
✅ 只爬公开可访问的数据（无需登录/付费）
✅ 遵守 robots.txt
✅ 控制频率，不影响目标站正常运行
✅ 不爬取个人隐私数据（手机号/身份证/地址）
✅ 数据仅用于个人学习/研究
❌ 不要绕过付费墙
❌ 不要破解加密接口后大规模商用
❌ 不要爬取后转售原始数据
❌ 不要对目标站造成 DDoS 效果
```

###  robots.txt 快速检查
```bash
curl -s "https://target.com/robots.txt" | head -30
# 看 Disallow 和 Crawl-delay
```

---

## 三十六、高级解析技巧

### Shadow DOM 穿透
```python
# Playwright 可以直接穿透 Shadow DOM
content = page.locator("host-element").locator("inner-element").text_content()
# 或用 JS
content = page.evaluate("""() => {
    const host = document.querySelector('host-element');
    return host.shadowRoot.querySelector('.target').textContent;
}""")
```

### iframe 内容提取
```python
# Playwright 切换 frame
frame = page.frame_locator("iframe#content-frame")
text = frame.locator(".article-body").text_content()

# requests 方案：先获取 iframe src，再单独请求
iframe_src = soup.select_one("iframe")["src"]
iframe_resp = session.get(urljoin(base_url, iframe_src))
```

### 无限滚动加载
```python
# Playwright：滚动到底部直到无新内容
last_height = 0
while True:
    page.evaluate("window.scrollTo(0, document.body.scrollHeight)")
    time.sleep(2)
    new_height = page.evaluate("document.body.scrollHeight")
    if new_height == last_height:
        break
    last_height = new_height

# 或拦截 XHR 请求直接拿 API 数据
page.on("response", lambda resp: handle_api(resp) if "/api/items" in resp.url else None)
```

### GraphQL 接口爬取
```python
# 1. 发现 GraphQL 端点（通常 /graphql 或 /api/graphql）
# 2. Introspection 获取完整 schema
query = """
{
  __schema {
    queryType { fields { name args { name type { name } } } }
  }
}
"""
resp = session.post("https://target.com/graphql", json={"query": query})
schema = resp.json()

# 3. 构造查询
query = """
query {
  products(first: 50, after: "cursor") {
    edges { node { name price description } }
    pageInfo { hasNextPage endCursor }
  }
}
"""
```

### WebSocket 数据抓取
```python
# Playwright 监听 WebSocket 消息
def handle_ws(ws):
    ws.on("framereceived", lambda data: save(data.payload))

page.on("websocket", handle_ws)
page.goto("https://target.com")  # 触发 WS 连接
```

### 分页模式识别
```python
# 常见分页模式：
# 1. URL 参数：?page=2, ?p=2, ?offset=20
# 2. 路径：/list/2.html, /page/2/
# 3. API cursor：{"next_cursor": "abc123"}
# 4. 无限滚动：无分页，靠 JS 加载
# 5. "加载更多"按钮：点击触发 AJAX

# 通用分页爬取
def crawl_all_pages(base_url, max_pages=100):
    for page_num in range(1, max_pages + 1):
        resp = fetch(f"{base_url}?page={page_num}")
        items = parse(resp)
        if not items:  # 空页 = 结束
            break
        yield from items
        time.sleep(random.uniform(1, 3))
```

---

## 三十七、测试与可维护性

### 选择器单元测试
```python
# 保存目标页面快照，定期验证选择器是否失效
import pytest
from pathlib import Path

SNAPSHOTS = Path("tests/snapshots")

def test_product_selector():
    html = (SNAPSHOTS / "target_page.html").read_text()
    soup = BeautifulSoup(html, "lxml")
    items = soup.select(".product-card")
    assert len(items) > 0, "选择器 .product-card 失效！"
    assert items[0].select_one(".title"), "子选择器 .title 失效！"
```

### 爬虫健康检查
```python
# 每次运行后输出健康报告
def health_report(stats):
    print(f"""
    ═══ 爬虫健康报告 ═══
    总请求: {stats['total']}
    成功: {stats['success']} ({stats['success']/stats['total']*100:.1f}%)
    失败: {stats['failed']}
    403/429: {stats['blocked']}
    平均响应: {stats['avg_time']:.2f}s
    数据条数: {stats['items']}
    空字段率: {stats['empty_rate']*100:.1f}%
    ═══════════════════
    """)
    if stats['blocked'] / stats['total'] > 0.1:
        print("⚠️ 封禁率 > 10%，建议降低频率或升级代理")
    if stats['empty_rate'] > 0.2:
        print("⚠️ 空字段率 > 20%，选择器可能失效")
```

### 版本化选择器配置
```yaml
# selectors.yaml — 选择器与代码分离，改版只改配置
target_site:
  version: "2026-07"
  list_page:
    container: ".product-grid"
    item: ".product-card"
    title: "h2.title"
    price: ".price-current"
    url: "a.link"
  detail_page:
    description: "#description"
    specs: "table.specs tr"
```

---

## 三十八、Sitemap 与结构化发现

### 利用 sitemap.xml 获取全站 URL
```python
import requests
from xml.etree import ElementTree

def get_all_urls_from_sitemap(domain):
    """从 sitemap 获取全站 URL，比 BFS 爬取更高效"""
    urls = []
    # 1. 检查 robots.txt 中的 Sitemap 声明
    robots = requests.get(f"{domain}/robots.txt").text
    sitemaps = [line.split(": ", 1)[1] for line in robots.splitlines() 
                if line.startswith("Sitemap:")]
    if not sitemaps:
        sitemaps = [f"{domain}/sitemap.xml"]
    
    # 2. 解析 sitemap（可能是 sitemap index）
    for sm_url in sitemaps:
        resp = requests.get(sm_url)
        root = ElementTree.fromstring(resp.content)
        ns = {"sm": "http://www.sitemaps.org/schemas/sitemap/0.9"}
        
        # sitemap index → 递归
        for loc in root.findall(".//sm:sitemap/sm:loc", ns):
            sub_resp = requests.get(loc.text)
            sub_root = ElementTree.fromstring(sub_resp.content)
            urls.extend(u.text for u in sub_root.findall(".//sm:url/sm:loc", ns))
        
        # 直接 URL 列表
        urls.extend(u.text for u in root.findall(".//sm:url/sm:loc", ns))
    
    return list(set(urls))
```

### 利用搜索引擎发现页面
```bash
# site: 语法发现某站所有被收录的页面
# Bing:
curl "https://www.bing.com/search?q=site:usc.edu.cn+推免" -H "UA..."
# 或用 DuckDuckGo HTML 版:
curl "https://html.duckduckgo.com/html/?q=site:usc.edu.cn+推免+名单"
```

---

## 三十九、压缩与传输优化

### 处理各种压缩
```python
# requests 自动处理 gzip/deflate
# 但 brotli 需要额外安装
# pip install brotli

import brotli
resp = session.get(url, headers={"Accept-Encoding": "gzip, deflate, br"})
if resp.headers.get("Content-Encoding") == "br":
    content = brotli.decompress(resp.content).decode()
else:
    content = resp.text  # requests 自动解压 gzip/deflate
```

### 条件请求（节省带宽）
```python
# ETag / Last-Modified 避免重复下载
headers = {}
if cached_etag:
    headers["If-None-Match"] = cached_etag
if cached_modified:
    headers["If-Modified-Since"] = cached_modified

resp = session.get(url, headers=headers)
if resp.status_code == 304:
    pass  # 未变化，用缓存
else:
    # 保存新的 ETag 和 Last-Modified
    cached_etag = resp.headers.get("ETag")
    cached_modified = resp.headers.get("Last-Modified")
```

### 流式下载大文件
```python
# 不要一次性加载到内存
def download_large(url, save_path, chunk_size=8192):
    with session.get(url, stream=True) as resp:
        resp.raise_for_status()
        with open(save_path, "wb") as f:
            for chunk in resp.iter_content(chunk_size):
                f.write(chunk)
```

---

## 四十、错误分类与告警

### 错误分级处理
```python
class SpiderError(Exception):
    pass

class RetryableError(SpiderError):
    """可重试：网络超时、5xx、429"""
    pass

class FatalError(SpiderError):
    """致命：选择器全部失效、域名不存在"""
    pass

class SoftBlockError(SpiderError):
    """软封禁：200 但内容是验证页"""
    pass

def handle_error(e, url, stats):
    if isinstance(e, RetryableError):
        stats['retried'] += 1
        return "retry"
    elif isinstance(e, SoftBlockError):
        stats['blocked'] += 1
        if stats['blocked'] > 5:
            notify("⚠️ 连续被软封禁，切换代理/降低频率")
        return "switch_proxy"
    elif isinstance(e, FatalError):
        notify(f"🚨 致命错误：{e}，爬虫停止")
        return "stop"
```

### 告警通知
```python
# 简单方案：写入日志 + 企业微信/钉钉 Webhook
import requests

def notify(msg):
    # 钉钉机器人
    requests.post("https://oapi.dingtalk.com/robot/send?access_token=xxx",
                  json={"msgtype": "text", "text": {"content": f"[爬虫告警] {msg}"}})
```

---

## 四十一、OCR 与图片文字识别

当内容是图片而非文本时（如公示名单扫描件）：

### 本地 OCR（免费）
```python
# pip install pytesseract pillow
# 需要安装 tesseract-ocr：apt install tesseract-ocr tesseract-ocr-chi-sim
import pytesseract
from PIL import Image

img = Image.open("notice.png")
text = pytesseract.image_to_string(img, lang="chi_sim+eng")
print(text)
```

### 表格图片 → 结构化
```python
# pip install paddleocr paddlepaddle
from paddleocr import PaddleOCR
ocr = PaddleOCR(use_angle_cls=True, lang="ch")
result = ocr.ocr("table.png")
for line in result[0]:
    box, (text, confidence) = line
    print(f"{text} ({confidence:.2f})")
```

### 在线 OCR API（精度更高）
```
• 百度 OCR：免费额度 500 次/天
• 腾讯 OCR：免费额度 1000 次/月
• Tesseract：免费但中文精度一般
• PaddleOCR：免费，中文精度高，推荐
```

---

## 四十二、数据管道与存储架构

### 小规模（< 10 万条）
```
爬虫 → JSON/CSV 文件 → pandas 分析
```

### 中规模（10 万 ~ 1000 万条）
```
爬虫 → SQLite / PostgreSQL → 定时 ETL → 分析报表
```

### 大规模（> 1000 万条）
```
爬虫集群 → Kafka/RabbitMQ → Spark/Flink → 数据湖(S3/MinIO)
                                    ↓
                              Elasticsearch（搜索）
                              ClickHouse（分析）
```

### 去重策略
```python
# 1. URL 级去重：Bloom Filter（内存高效）
# pip install pybloom-live
from pybloom_live import BloomFilter
bf = BloomFilter(capacity=10000000, error_rate=0.001)
if url not in bf:
    bf.add(url)
    crawl(url)

# 2. 内容级去重：SimHash（近似重复检测）
# 适合新闻/文章类内容，标题略有不同但正文相同

# 3. 增量标记：记录每个 URL 的 last_crawled 时间
# 只重新爬取 updated_after > last_crawled 的页面
```

---

## 四十三、GraphQL/WebSocket/动态数据深度处理

### GraphQL 深度爬取
```python
# 1. 发现 GraphQL 端点后，先做 introspection 获取 schema
introspection_query = """
query IntrospectionQuery {
  __schema {
    queryType { name }
    mutationType { name }
    types {
      name
      fields {
        name
        args { name type { name kind ofType { name kind } } }
        type { name kind ofType { name kind } }
      }
    }
  }
}
"""
resp = session.post(graphql_url, json={"query": introspection_query})
schema = resp.json()

# 2. 分页查询（cursor-based pagination）
def crawl_graphql_all(session, graphql_url, query, cursor=None, page_size=50):
    """游标分页爬取全部数据"""
    variables = {"first": page_size, "after": cursor}
    resp = session.post(graphql_url, json={"query": query, "variables": variables})
    data = resp.json()["data"]
    
    # 假设返回结构：{ items { edges { node { ... } } pageInfo { hasNextPage endCursor } } }
    edges = data["items"]["edges"]
    nodes = [e["node"] for e in edges]
    page_info = data["items"]["pageInfo"]
    
    if page_info["hasNextPage"]:
        nodes.extend(crawl_graphql_all(
            session, graphql_url, query, 
            cursor=page_info["endCursor"], page_size=page_size
        ))
    return nodes
```

### WebSocket 实时数据拦截
```python
# Playwright 拦截 WebSocket 消息
ws_messages = []

def on_message(ws, payload):
    """WebSocket 消息回调"""
    if isinstance(payload, str):
        import json
        try:
            data = json.loads(payload)
            ws_messages.append(data)
        except json.JSONDecodeError:
            ws_messages.append({"raw": payload})

def on_websocket(ws):
    ws.on("framereceived", lambda data: on_message(ws, data.get("payload", "")))

page.on("websocket", on_websocket)
page.goto("https://target.com/live-data")
page.wait_for_timeout(10000)  # 等 10s 收集数据
# ws_messages 中就是所有实时推送的 JSON 数据
```

### SSE（Server-Sent Events）拦截
```python
# 某些站用 SSE 推送数据（单向流）
# Playwright 拦截
sse_data = []

def on_response(response):
    if "text/event-stream" in response.headers.get("content-type", ""):
        body = response.text()
        for line in body.split("\n"):
            if line.startswith("data:"):
                sse_data.append(json.loads(line[5:].strip()))

page.on("response", on_response)
page.goto("https://target.com")
```

### 拦截所有 XHR/Fetch 请求
```python
# 通用方案：拦截所有 AJAX 请求，按 API 路径分类收集
api_responses = {}  # {path_pattern: [response_data, ...]}

def handle_response(response):
    url = response.url
    content_type = response.headers.get("content-type", "")
    if "application/json" not in content_type:
        return
    
    try:
        data = response.json()
    except:
        return
    
    # 按路径模式分类
    for pattern in ["/api/v1/products", "/api/v1/reviews", "/graphql"]:
        if pattern in url:
            api_responses.setdefault(pattern, []).append(data)
            break

page.on("response", handle_response)
page.goto("https://target.com")
# 正常操作页面（滚动、点击等），所有 API 响应自动被收集
```

### 反 GraphQL 限制绕过
```python
# 某些站对 GraphQL 加了限制：
# 1. 查询复杂度限制 → 拆分大查询为多个小查询
# 2. 速率限制 → 降低频率 + 代理轮换
# 3. 需要鉴权 → 从浏览器 DevTools 提取 Authorization header
# 4. 禁止 introspection → 从前端 JS bundle 中提取 schema

# 从 JS bundle 提取 GraphQL 操作
import re
js_content = session.get("https://target.com/static/app.js").text
operations = re.findall(r'query\s+(\w+)\s*\(([^)]*)\)\s*\{', js_content)
mutations = re.findall(r'mutation\s+(\w+)\s*\(([^)]*)\)\s*\{', js_content)
# 这些就是网站实际使用的所有 GraphQL 操作名和参数
```

---

## 四十四、蜜罐与陷阱识别

### 常见蜜罐类型
```
1. 隐藏链接蜜罐
   • <a href="/trap" style="display:none"> 或 position:absolute; left:-9999px
   • 真人看不到，爬虫会跟着爬 → 触发封禁
   • 防御：只爬可见元素（检查 computed style）

2. robots.txt 蜜罐
   • Disallow: /secret-trap-page
   • 遵守 robots 的爬虫不会去，不遵守的会去 → 暴露身份
   • 防御：遵守 robots.txt

3. 文本蜜罐
   • 页面中隐藏文字 "如果你是爬虫，请访问 /bot-page"
   • 防御：不解析不可见文本中的指令

4. 频率蜜罐
   • 正常用户不可能 1 秒内访问 50 个页面
   • 防御：限速 + 随机延迟

5. 参数蜜罐
   • URL 中加入无意义参数 ?session=abc123
   • 如果爬虫原样保留所有参数 → 暴露
   • 防御：只保留必要参数

6. 时间戳蜜罐
   • 页面中嵌入隐藏时间戳，提交时校验
   • 如果爬虫秒级提交 → 不是人类
   • 防御：模拟阅读时间
```

### 检测可见性
```python
def is_visible(el):
    """判断元素是否对真人可见"""
    style = el.get("style", "")
    if "display:none" in style.replace(" ", ""):
        return False
    if "visibility:hidden" in style.replace(" ", ""):
        return False
    # 检查 class 中是否有隐藏类
    classes = el.get("class", [])
    hidden_classes = {"hidden", "sr-only", "visually-hidden", "d-none"}
    if hidden_classes & set(classes):
        return False
    return True

# 只跟踪可见链接
links = [a for a in soup.select("a[href]") if is_visible(a)]
```

---

## 四十五、截图与证据保全

### 网页截图（Playwright）
```python
# 全页截图
page.goto(url, wait_until="networkidle")
page.screenshot(path="evidence.png", full_page=True)

# 指定区域
el = page.locator(".target-section")
el.screenshot(path="section.png")

# 生成 PDF
page.pdf(path="page.pdf", format="A4")
```

### 带时间戳的归档截图
```python
from datetime import datetime
ts = datetime.now().strftime("%Y%m%d_%H%M%S")
page.screenshot(path=f"archive/{domain}_{ts}.png", full_page=True)
# 配合 changedetection.io 可自动定期截图对比
```

### MHTML 完整归档
```python
# MHTML 格式保存整个页面（HTML+CSS+图片+JS，单文件）
# Playwright 通过 CDP 协议导出
client = page.context.new_cdp_session(page)
result = client.send("Page.captureSnapshot", {"format": "mhtml"})
with open(f"archive/{ts}.mhtml", "w", encoding="utf-8") as f:
    f.write(result["data"])
```

### Wayback Machine 存档
```python
# 主动提交 URL 到 Internet Archive
import requests
def save_to_wayback(url):
    """将页面提交到 web.archive.org 存档"""
    resp = requests.get(f"https://web.archive.org/save/{url}")
    if resp.status_code == 200:
        # 返回存档 URL
        archive_url = resp.headers.get("Content-Location", "")
        return f"https://web.archive.org{archive_url}"
    return None

# 查询已有存档
def check_wayback(url):
    api = f"https://archive.org/wayback/available?url={url}"
    resp = requests.get(api).json()
    if "archived_snapshots" in resp:
        return resp["archived_snapshots"].get("closest", {}).get("url")
    return None
```

### 证据完整性校验
```python
import hashlib, json
from datetime import datetime

def create_evidence_record(url, screenshot_path, html_path):
    """创建带哈希的证据记录"""
    # 计算文件哈希
    with open(screenshot_path, "rb") as f:
        img_hash = hashlib.sha256(f.read()).hexdigest()
    with open(html_path, "rb") as f:
        html_hash = hashlib.sha256(f.read()).hexdigest()
    
    record = {
        "url": url,
        "timestamp": datetime.now().isoformat(),
        "screenshot_sha256": img_hash,
        "html_sha256": html_hash,
        "screenshot_path": screenshot_path,
        "html_path": html_path,
    }
    
    # 追加到证据日志
    with open("evidence_log.jsonl", "a") as f:
        f.write(json.dumps(record, ensure_ascii=False) + "\n")
    return record
```

### 元数据保全
```python
# 保存 HTTP 响应头（含服务器信息、缓存策略等）
evidence = {
    "url": resp.url,
    "status_code": resp.status_code,
    "headers": dict(resp.headers),
    "content_length": len(resp.content),
    "content_type": resp.headers.get("content-type"),
    "server": resp.headers.get("server"),
    "date": resp.headers.get("date"),
    "last_modified": resp.headers.get("last-modified"),
    "captured_at": datetime.now().isoformat(),
}
```

---

## 四十六、AI Agent 爬取范式（2026 前沿）

### browser-use（⭐106K）
```python
# pip install browser-use
from browser_use import Agent
from langchain_openai import ChatOpenAI

agent = Agent(
    task="去南华大学研究生院官网，找到2026届推免生公示名单，下载PDF",
    llm=ChatOpenAI(model="gpt-4o"),
)
result = await agent.run()
# AI 自主决定：打开哪个页面、点哪个链接、怎么下载
```

### 与传统爬虫的对比
```
传统爬虫：你写死选择器 → 网站改版就挂
AI Agent：你描述目标 → AI 自主导航 → 自适应变化

适用场景：
• 一次性任务（不值得写爬虫）
• 目标站结构未知/频繁变化
• 需要多步交互（登录→搜索→筛选→下载）
• 跨站聚合（从多个来源收集信息）

不适用：
• 大规模高频爬取（太慢太贵）
• 需要精确控制每个请求
• 对延迟敏感的场景
```

### MCP（Model Context Protocol）集成
```
Crawl4AI / Scrapling / Firecrawl 都支持 MCP server：
• AI Agent 通过 MCP 调用爬虫工具
• 实现 "AI 决定爬什么 + 爬虫负责怎么爬" 的分工
• Claude Code / Cursor / 其他 AI IDE 可直接调用
```

---

## 四十七、SPA 单页应用爬取策略

React/Vue/Angular 等 SPA 的内容全靠 JS 渲染，传统 HTTP 请求拿到的是空壳。

### 判断是否是 SPA
```bash
curl -s "https://target.com" | wc -c
# 如果 HTML 只有几 KB（< 5KB），大概率是 SPA
# 正常服务端渲染页面通常 > 50KB
```

### 方案选择
```
方案 A：找底层 API（最优）
  • DevTools → Network → XHR/Fetch → 找到数据接口
  • 直接调 API，跳过渲染，速度最快
  • 常见路径：/api/v1/xxx, /graphql, /_next/data/xxx

方案 B：Playwright 渲染
  • 等待关键元素出现：page.wait_for_selector(".data-loaded")
  • 等待网络空闲：wait_until="networkidle"
  • 拦截 API 响应：page.on("response", handler)

方案 C：预渲染服务
  • 某些站有 ?_escaped_fragment_= 或 ?outputType=amp
  • Next.js 站点可能有 /_next/data/ 路径
  • Nuxt.js 站点可能有 /_payload.json
```

### 拦截 XHR 获取原始数据
```python
# Playwright：拦截 API 响应，比解析 DOM 更稳定
import json

collected = []

def handle_response(response):
    if "/api/products" in response.url:
        data = response.json()
        collected.extend(data["items"])

page.on("response", handle_response)
page.goto("https://spa-site.com/products")
page.wait_for_timeout(3000)  # 等待请求完成
# collected 中就是原始 JSON 数据，无需解析 DOM
```

### Next.js / Nuxt.js 特殊路径
```python
# Next.js：页面数据在 /_next/data/{buildId}/{path}.json
# 从页面 HTML 中的 __NEXT_DATA__ script 标签获取 buildId
import re
html = resp.text
match = re.search(r'"buildId":"([^"]+)"', html)
build_id = match.group(1)
data_url = f"https://target.com/_next/data/{build_id}/products.json"
data = session.get(data_url).json()

# Nuxt.js：/__nuxt 或 /_payload.json
```

---

## 四十八、爬虫可观测性

### 结构化日志
```python
import logging, json

class JsonFormatter(logging.Formatter):
    def format(self, record):
        return json.dumps({
            "ts": self.formatTime(record),
            "level": record.levelname,
            "msg": record.getMessage(),
            "url": getattr(record, "url", None),
            "status": getattr(record, "status", None),
            "duration_ms": getattr(record, "duration_ms", None),
        })

handler = logging.FileHandler("spider.jsonl")
handler.setFormatter(JsonFormatter())
log = logging.getLogger("spider")
log.addHandler(handler)

# 使用
log.info("fetched", extra={"url": url, "status": 200, "duration_ms": 342})
```

### 关键指标监控
```
• 成功率：success / total（< 90% 告警）
• 封禁率：blocked / total（> 5% 需降速）
• 平均响应时间：突增 3x 可能被限流
• 数据完整率：非空字段占比（< 80% 选择器可能失效）
• 吞吐量：pages/min（下降可能代理质量变差）
• 去重率：重复数据占比（> 20% 爬取逻辑有问题）
```

### 简单 Dashboard（Grafana + Prometheus 或纯文件）
```python
# 轻量方案：每次运行写一行 JSON 到 metrics.jsonl
import time
metrics = {
    "run_id": run_id,
    "timestamp": time.time(),
    "total_requests": stats["total"],
    "success_rate": stats["success"] / stats["total"],
    "blocked_rate": stats["blocked"] / stats["total"],
    "items_collected": stats["items"],
    "duration_seconds": stats["duration"],
}
with open("metrics.jsonl", "a") as f:
    f.write(json.dumps(metrics) + "\n")
```

---

## 四十九、常见 CMS 平台爬取特征

### WordPress
```
• 文章列表：/wp-json/wp/v2/posts?page=1&per_page=100（REST API 公开）
• 分类：/wp-json/wp/v2/categories
• 搜索：/wp-json/wp/v2/search?search=关键词
• 分页头：X-WP-Total（总数）、X-WP-TotalPages（总页数）
• 无需解析 HTML，直接调 REST API
```

### Drupal
```
• JSON API：/jsonapi/node/article?page[limit]=50
• 需要 Accept: application/vnd.api+json 头
• 分页用 page[offset] 和 page[limit]
```

### 织梦（DedeCMS）/ 帝国CMS（国内常见）
```
• 列表页：/plus/list.php?tid=1&page=2
• 文章页：/a/news/2025/0101/1234.html
• 编码常为 GBK：resp.encoding = 'gbk'
• 反爬弱：带 UA + 限速即可
```

### 政府/高校站（如南华大学）
```
• 多为静态生成或老式 CMS
• 路径特征：/info/1988/8767.htm（栏目ID/文章ID）
• 分页：/tzgg/1.htm, /tzgg/2.htm
• 编码：UTF-8 或 GBK 混合
• 反爬：基本没有，curl 即可
• 注意：公示文件可能是 .doc/.xls 附件
```

---

## 五十、RSS/Atom 与结构化数据源

### 优先检查是否有 RSS
```bash
# 常见 RSS 路径
curl -s "https://target.com/feed" -o /dev/null -w "%{http_code}"
curl -s "https://target.com/rss" -o /dev/null -w "%{http_code}"
curl -s "https://target.com/atom.xml" -o /dev/null -w "%{http_code}"
# 或检查 HTML head 中的 link 标签
curl -s "https://target.com" | grep -i 'type="application/rss'
```

### 解析 RSS/Atom
```python
# pip install feedparser
import feedparser

feed = feedparser.parse("https://target.com/feed")
for entry in feed.entries:
    print(entry.title, entry.link, entry.published)
```

### 结构化数据（JSON-LD / Schema.org）
```python
# 很多站在 <script type="application/ld+json"> 中嵌入结构化数据
import json
scripts = soup.select('script[type="application/ld+json"]')
for s in scripts:
    data = json.loads(s.string)
    # data 通常包含：文章标题、作者、日期、价格等
    print(data.get("@type"), data.get("name"))
```

---

## 五十一、爬虫与 AI/LLM 数据准备

### 为 RAG/知识库爬取内容
```python
# 目标：爬取文档站 → 分块 → 存入向量数据库
# 1. 爬取（用 Crawl4AI 或 Firecrawl 输出 Markdown）
# 2. 分块（按标题/段落/固定 token 数）
# 3. 嵌入（text-embedding-3-small 等）
# 4. 存储（ChromaDB / FAISS / Pinecone）

# Crawl4AI 全站爬取文档站
# crawl4ai-skill crawl-site https://docs.target.com --max-pages 200 --format fit_markdown
```

### 为训练数据爬取
```
注意事项：
• 检查网站 ToS 是否允许用于 AI 训练
• 去重（MinHash / SimHash）
• 质量过滤（语言检测、困惑度过滤）
• 去除 PII（个人信息）
• 记录来源 URL（可溯源）
• 遵守 robots.txt 和 Crawl-delay
```

### MCP Server 集成
```
支持的爬虫 MCP Server：
• Crawl4AI：docker 部署后暴露 MCP 端点
• Scrapling：内置 MCP server
• Firecrawl：云端 MCP
• webclaw：Rust 实现，本地 MCP

AI Agent 通过 MCP 调用：
  "爬取 https://xxx.com 的内容" → MCP → 爬虫执行 → 返回 Markdown
```

---

## 五十二、实战检查清单（每次爬取前过一遍）

```
□ 1. 目标确认
  □ 确认目标 URL 和数据字段
  □ 检查是否有公开 API（DevTools Network）
  □ 检查 robots.txt
  □ 确认数据是否公开可访问

□ 2. 反爬评估
  □ curl 测试：能拿到完整 HTML？
  □ 是否需要 JS 渲染？
  □ 是否有 Cloudflare/验证码？
  □ 频率限制如何？

□ 3. 方案选择
  □ 静态 → curl/requests
  □ JS → Playwright/Patchright
  □ 强反爬 → CloakBrowser/Camoufox
  □ 大规模 → Crawlee/Scrapy

□ 4. 基础设施
  □ 需要代理吗？什么类型？
  □ 需要持久化 cookie 吗？
  □ 输出格式：JSON/CSV/SQLite？
  □ 需要断点续爬吗？

□ 5. 健壮性
  □ 重试逻辑（指数退避）
  □ 错误分类（可重试 vs 致命）
  □ 选择器失效检测
  □ 编码处理

□ 6. 合规
  □ 限速（不超过 Crawl-delay）
  □ 不爬个人隐私数据
  □ 不影响目标站正常运行
  □ 数据用途合法

□ 7. 验证
  □ 数据完整性检查
  □ 去重
  □ 抽样人工核对
  □ 健康报告输出
```

---

## 五十三、代理基础设施搭建

### 自建代理池（免费方案）
```python
# 抓取免费代理 + 验证 + 存储
# 来源：快代理、西刺、89免费代理等（质量低，仅适合测试）

import requests
from concurrent.futures import ThreadPoolExecutor

def validate_proxy(proxy):
    """验证代理是否可用"""
    try:
        resp = requests.get("https://httpbin.org/ip",
                          proxies={"http": proxy, "https": proxy},
                          timeout=5)
        if resp.status_code == 200:
            latency = resp.elapsed.total_seconds()
            return {"proxy": proxy, "ip": resp.json()["origin"], "latency": latency}
    except:
        pass
    return None

# 验证后按延迟排序，取最快的
valid = [r for r in results if r]
valid.sort(key=lambda x: x["latency"])
```

### 付费代理服务对比
| 服务商 | 类型 | 价格 | 特点 |
|--------|------|------|------|
| Bright Data | 住宅/移动/ISP | $5.04/GB起 | 最大池（7200万IP），功能最全 |
| Oxylabs | 住宅/数据中心 | $6/GB起 | 企业级，AI 爬虫优化 |
| Smartproxy | 住宅/ISP | $4/GB起 | 性价比高，API 简单 |
| IPRoyal | 住宅 | $1.75/GB起 | 便宜，适合小规模 |
| NodeMaven | 住宅/移动 | 按量 | 高 IP 质量，fraud score < 97% |
| 快代理 | 国内隧道 | ¥0.04/IP起 | 国内站适用 |
| 芝麻代理 | 国内住宅 | 按量 | 国内站适用 |

### 代理使用最佳实践
```python
# 1. 住宅代理用于反爬站，数据中心代理用于无防护站
# 2. 粘性会话：同一任务用同一出口 IP（避免会话断裂）
# 3. 轮换频率：每 5~20 个请求换一次 IP
# 4. geoip 匹配：代理出口国的时区/语言要一致
# 5. SOCKS5 优先：避免 HTTP CONNECT 的 HTTP/2 兼容问题
# 6. 失败自动切换：代理报错立即换下一个

class ProxyRotator:
    def __init__(self, proxies):
        self.proxies = proxies
        self.index = 0
        self.failed = set()
    
    def get_next(self):
        available = [p for p in self.proxies if p not in self.failed]
        if not available:
            self.failed.clear()  # 全部失败则重置
            available = self.proxies
        proxy = available[self.index % len(available)]
        self.index += 1
        return proxy
    
    def mark_failed(self, proxy):
        self.failed.add(proxy)
```

---

## 五十四、反爬服务商 API（终极方案）

当自建方案成本太高时，直接用第三方反爬 API：

### 工作原理
```
你的请求 → 反爬 API 服务 → 他们的基础设施（浏览器集群+住宅代理+验证码解决）
         ← 返回干净的 HTML/JSON ←
```

### 主要服务商
| 服务 | 特点 | 价格 |
|------|------|------|
| **Scrapfly** | 全托管，支持 JS 渲染+截图+AI 提取 | $0.001/请求起 |
| **ScrapeOps** | 代理+渲染+反爬一体 | $0.002/请求起 |
| **ZenRows** | 专注反爬绕过，自动处理验证码 | $0.003/请求起 |
| **HyperSolutions** | 专攻 Akamai/DataDome/Kasada token 生成 | 按量 |
| **RiskBypass** | Shape/Kasada/PerimeterX/Akamai 全套 | 按量 |
| **Crawlbase** | 99% 成功率，智能代理 | $0.002/请求起 |
| **Scrappey** | 按成功计费，含住宅代理 | 按成功请求 |

### 使用示例
```python
# Scrapfly
import requests
resp = requests.get(
    "https://api.scrapfly.io/scrape",
    params={
        "key": "YOUR_API_KEY",
        "url": "https://heavily-protected-site.com",
        "render_js": "true",
        "anti_bot": "true",
        "country": "us",
    }
)
html = resp.json()["result"]["content"]

# ZenRows
resp = requests.get(
    "https://api.zenrows.com/v1/",
    params={
        "apikey": "YOUR_KEY",
        "url": "https://target.com",
        "js_render": "true",
        "premium_proxy": "true",
    }
)
```

### 何时用第三方 API vs 自建
```
用第三方 API：
• 目标站反爬极强（Kasada/Akamai/PerimeterX）
• 需要快速出结果，没时间调试
• 爬取量不大（< 10 万页/月）
• 不想维护浏览器集群和代理池

自建：
• 爬取量大（> 100 万页/月），API 成本太高
• 目标站反爬中等，CloakBrowser 能搞定
• 需要完全控制请求细节
• 数据敏感，不能经过第三方
```

---

## 五十五、版本历史与更新日志

### 版本演进
```
v1 (初始)：基础工具链 + 实战经验
  • curl + grep 快速抓取
  • Playwright JS 渲染
  • requests + BS4 批量抓取
  • 最佳实践 + 中国大学官网经验

v2：+ 技能生态整合
  • crawl4ai-skill（搜索+爬取一体化）
  • algo-seo-crawl（BFS 全站爬虫管线）
  • spider-ops（spider-scaffold 生产模板 + spider-fix 自修复 + spider-data 清洗入库）

v3：+ 反检测深度
  • Crawlee 高级反检测架构（指纹+会话池+分层代理+自动并发）
  • 反检测浏览器生态（CloakBrowser/Camoufox/Patchright/Botright）
  • TLS 指纹伪装（tls-client/curl-impersonate/Impit）
  • AI 驱动爬取（Jina Reader/Firecrawl/Crawl4AI）

v4：全面扩展
  • Scrapling 自适应选择器框架
  • WAF 分级绕过实战（Cloudflare/DataDome/Kasada/Akamai）
  • 行为伪装（贝塞尔曲线鼠标/随机打字/人类滚动）
  • API 逆向与抓包（mitmproxy/签名破解）
  • 页面监控与增量爬取（changedetection.io）
  • 分布式爬虫与代理池（Scrapy-Redis/Crawlab/Gerapy）
  • JS 逆向与反混淆（AST 变换/字符串数组还原）
  • 正文提取算法（trafilatura/newspaper3k）
  • 性能优化（异步并发/连接池/DNS 缓存）
  • 指纹检测向量详解（6 大类 20+ 检测点）
  • 常见坑与反模式（8 个典型错误）
  • Cookie 与会话管理策略
  • 调度与编排（cron/Celery/Airflow）
  • 特殊场景（电商/社交/新闻/政府）
  • 数据质量保障（校验清单/异常检测）
  • HTTP/2 指纹与高级检测
  • 表格/PDF/图片爬取
  • 限流检测与自适应调速（AIMD 算法）
  • 中国平台特殊处理（微信/知乎/B站/淘宝/百度）
  • 反爬对抗升级路线图（9 阶段）
  • 法律与合规要点
  • 高级解析技巧（Shadow DOM/iframe/无限滚动/GraphQL/WebSocket）
  • 测试与可维护性（选择器单元测试/健康检查/版本化配置）
  • Sitemap 与结构化发现
  • 压缩与传输优化（Brotli/条件请求/流式下载）
  • 错误分类与告警
  • OCR 与图片文字识别
  • 数据管道与存储架构（小规模/中规模/大规模）
  • 蜜罐与陷阱识别（6 类蜜罐）
  • 截图与证据保全（MHTML/Wayback/哈希校验）
  • AI Agent 爬取范式（browser-use/MCP 集成）
  • SPA 单页应用爬取策略
  • 爬虫可观测性（结构化日志/指标监控）
  • 常见 CMS 平台爬取特征（WordPress/Drupal/织梦/政府站）
  • RSS/Atom 与结构化数据源（JSON-LD）
  • 爬虫与 AI/LLM 数据准备
  • 实战检查清单（7 大类 25+ 项）
  • 代理基础设施搭建（自建/付费/最佳实践）
  • 反爬服务商 API（Scrapfly/ZenRows/HyperSolutions）
  • 快速决策流程图
  • 工具安装一键脚本
  • 参考资源（文档/测试站/社区/书籍/课程/在线工具）
  • 反爬对抗心理学
  • 实战案例（爬取高校推免名单完整流程）
  • 多语言/国际化爬取
  • 爬虫安全与防护（Docker 隔离/日志脱敏/数据加密）
  • 技术栈全景图
  • GraphQL/WebSocket/动态数据深度处理

v5（最终版）：GitHub 深挖 + 新兴技术
  • 高级代理策略（AWS API Gateway 无限 IP 池/requests-ip-rotator ⭐1669）
  • Lambda 代理池（无状态爬虫，每次全新环境）
  • 社交媒体爬虫（Scweet Twitter⭐1569/GramAddict Instagram⭐1590/LinkedIn⭐769）
  • YouTube 评论爬取（youtube-comment-suite ⭐315）
  • Google Maps 数据爬取（google-maps-scraper ⭐5217，Go 实现）
  • 垂直领域爬虫（Amazon⭐437/房地产 HomeHarvest⭐715）
  • 开源反检测浏览器（undetectable-fingerprint-browser ⭐824，免费 Multilogin 替代）
  • Fortress 隐身 Chromium（⭐395）
  • Mochi 高保真指纹（⭐216）
  • Go 语言高性能爬虫（HTTPCloak ⭐1173，完美 TLS 指纹）
  • 浏览器自动化基准测试（browsers-benchmark ⭐358，绕过率对比）
  • CDP 协议补丁（CDP-Patches ⭐163，OS 级别反检测）
  • 新兴趋势总结（云原生代理/无 API 社交爬取/开源反检测/Go 高性能/基准测试驱动）
  
  深挖扩展轮次：
  • 最佳实践扩展（项目结构/开发流程/代码规范/日志规范）
  • 环境依赖扩展（分类安装/字体包/一键脚本）
  • 搜索引擎扩展（6 引擎对比/实战命令/SearXNG 自建）
  • 中国大学官网扩展（6 种 CMS 路径/编码处理/批量爬取）
  • BFS 爬虫完整 Python 实现 + 软 404 检测
  • 自修复协议完整代码（SpiderDoctor 诊断器 + 知识库系统）
  • TLS 指纹详解（JA3 哈希对比/tls-client/curl_cffi/验证方法）
  • 截图保全扩展（MHTML/Wayback Machine/哈希校验/元数据）
  • 参考资源扩展（9 检测站/书籍/GitHub 列表/在线工具）
  • 移动端 APP API 爬取（mitmproxy/Frida/SSL Pinning/模拟器/签名算法）
  • 登录与认证流程（Cookie/JWT/OAuth/2FA/扫码/会话管理）
  • 验证码规避策略（5 大触发因素/隐身延迟/行为模拟/会话预热/检测降级）
  
  GitHub 深挖轮次 2（多语言与实战）：
  • 多语言爬虫生态全景（Go/Rust/Java/C#/PHP/Elixir 等 10+ 语言）
  • Colly ⭐25383（Go，1000+ req/s）
  • Geziyor ⭐2773（Go，8748 req/s，JS 渲染）
  • 可视化/无代码爬虫（EasySpider ⭐44278 等）
  • 域名特定爬虫实战（35+ 热门网站模板：电商/房产/招聘/社交）
  • 文档解析深度指南（Dedoc ⭐716/PDF 表格提取/OCR 流程）
  • User-Agent 管理策略（crawler-user-agents ⭐1387/轮换/完整请求头）
  • 数据质量与验证框架（Pydantic/Schema/完整性/一致性检查）
  • 错误恢复与弹性设计（指数退避/断路器模式/降级策略）
  • 配置管理最佳实践（分层配置/.env/多环境/验证）
```

### 更新原则
- 每个新工具/技术经过实际验证后才纳入
- 优先收录高星（>1K⭐）项目
- 代码示例必须可运行
- 中文场景优先（大学官网、国内平台）

---

## 五十六、快速决策流程图（遇到新目标时）

```
收到爬取任务
    │
    ├─ 有公开 API / RSS / sitemap？
    │   └─ YES → 直接调 API / 解析 RSS / 读 sitemap（最快最稳）
    │
    ├─ curl 能拿到完整内容？
    │   └─ YES → curl + grep / requests + BS4
    │
    ├─ 需要 JS 渲染？
    │   ├─ 轻度（少量动态内容）→ Playwright / Patchright
    │   └─ 有反爬检测？
    │       ├─ Cloudflare Turnstile → CloakBrowser / Trawl
    │       ├─ DataDome / Imperva → CloakBrowser headed + humanize + 住宅代理
    │       └─ Kasada / Akamai → CloakBrowser Pro + 字体 + 住宅代理
    │
    ├─ 需要大规模（> 1 万页）？
    │   ├─ 静态 → Scrapy / Crawlee + 代理池
    │   └─ 动态 → Crawlee PlaywrightCrawler / Scrapling Spider
    │
    ├─ 一次性任务 / 结构未知？
    │   └─ AI Agent（browser-use）/ Jina Reader / Firecrawl
    │
    └─ 反爬极强 + 预算允许？
        └─ 第三方 API（Scrapfly / ZenRows / HyperSolutions）
```

---

## 五十七、工具安装一键脚本

```bash
#!/bin/bash
# spider-env-setup.sh — 一键搭建爬虫环境

set -e

echo "=== 基础依赖 ==="
apt-get update && apt-get install -y python3-pip xvfb \
    fonts-noto-color-emoji fonts-freefont-ttf fonts-unifont \
    fonts-ipafont-gothic fonts-wqy-zenhei fonts-tlwg-loma-otf

echo "=== Python 包 ==="
pip3 install --break-system-packages \
    requests beautifulsoup4 lxml urllib3 chardet \
    playwright tls-client browserforge \
    scrapling crawl4ai trafilatura \
    feedparser pandas pymupdf

echo "=== 浏览器 ==="
python3 -m playwright install chromium

echo "=== 可选：反检测浏览器 ==="
# pip3 install --break-system-packages cloakbrowser camoufox botright patchright

echo "=== 可选：分布式 ==="
# pip3 install --break-system-packages scrapy scrapy-redis celery

echo "=== 完成 ==="
echo "验证: python3 -c 'import requests, bs4, playwright; print(\"OK\")'"
```

---

## 五十八、参考资源

### 官方文档
- Crawlee：https://crawlee.dev/python/
- Scrapling：https://scrapling.readthedocs.io
- Camoufox：https://camoufox.com
- CloakBrowser：https://github.com/CloakHQ/CloakBrowser
- Playwright：https://playwright.dev/python/
- Firecrawl：https://docs.firecrawl.dev
- Crawl4AI：https://docs.crawl4ai.com
- Scrapy：https://docs.scrapy.org

### 检测测试站（验证你的爬虫隐身效果）
| 测试站 | 检测项 |
|--------|--------|
| https://bot.sannysoft.com | 基础自动化检测（webdriver 等） |
| https://abrahamjuliot.github.io/creepjs/ | 深度指纹检测（最全面） |
| https://browserleaks.com | 浏览器泄露检查（IP/WebRTC/字体等） |
| https://nopecha.com/demo/turnstile | Cloudflare Turnstile 测试 |
| https://antoinevastel.com/bots/datadome | DataDome 测试 |
| https://demo.fingerprint.com/playground | FingerprintJS 测试 |
| https://pixelscan.net | 像素级渲染检测 |
| https://incolumitas.com/pages/BotOrNot/ | 机器人/人类分类器 |
| https://nowsecure.nl | 综合反爬挑战（Cloudflare + 多 WAF） |

### 社区与博客
- **Reddit**：r/webscraping、r/datascience
- **Discord**：Scrapling 社区、Crawl4AI 社区
- **博客**：ScrapingBee Blog、Apify Blog、Zyte Blog、Oxylabs Blog
- **中文社区**：知乎"爬虫"话题、CSDN 爬虫板块、掘金爬虫标签

### 书籍
| 书名 | 作者 | 侧重 |
|------|------|------|
| Web Scraping with Python | Ryan Mitchell | 入门经典 |
| Learning Scrapy | Dimitrios Kouzis-Loukas | Scrapy 框架 |
| Practical Web Scraping for Data Science | Seppe vanden Broucke | 数据科学视角 |
| 精通 Python 爬虫 |  various | 中文实战 |

### GitHub Awesome 列表
- awesome-web-scraping（综合爬虫资源）
- awesome-scrapy（Scrapy 生态）
- awesome-crawl4ai（Crawl4AI 生态）
- awesome-browser-automation（浏览器自动化）

### 视频教程
- YouTube: "Web Scraping with Python" by Corey Schafer
- YouTube: "Advanced Web Scraping" by Tech With Tim
- B 站搜索："Python 爬虫实战"、"Scrapy 教程"

### 在线工具
| 工具 | 用途 |
|------|------|
| https://httpbin.org | HTTP 请求测试/调试 |
| https://requestbin.com | 查看你的请求长什么样 |
| https://regex101.com | 正则表达式测试 |
| https://jsonpath.com | JSONPath 表达式测试 |
| https://cssselector.net | CSS 选择器测试 |
| https://astexplorer.net | JS AST 可视化（逆向用） |

---

## 五十九、反爬对抗心理学

### 理解反爬工程师的思路
```
反爬系统的目标不是"阻止所有爬虫"，而是：
1. 提高爬虫成本（让爬取变得昂贵/缓慢）
2. 降低数据质量（让爬到的数据不可靠）
3. 识别并封禁（精准打击高频/异常流量）
4. 法律威慑（ToS + 技术手段配合诉讼）
```

### 博弈策略
```
• 不要和反爬系统"硬刚"→ 成本会指数级上升
• 找到"最弱路径"：移动端 API > 桌面 Web > 小程序
• 利用"合法通道"：RSS、sitemap、公开 API、数据合作
• 降低"异常度"：混入正常流量中，而非试图完全伪装
• 时间差攻击：反爬规则更新有延迟，新规则生效前快速完成
• 分散风险：多 IP + 多 UA + 多时段，避免单点被封
```

### 何时放弃
```
• 目标站用了 Kasada/Akamai 全套 + 你只有免费资源 → 考虑第三方 API
• 数据量小（< 100 页）→ 手动复制可能比写爬虫更快
• 法律风险高（金融/医疗/个人隐私）→ 不值得
• 目标站有官方数据合作渠道 → 走正规途径
```

---

## 六十、实战案例：爬取高校推免名单

以南华大学为例，完整流程：

### Step 1：定位信息源
```bash
# 教务处通知公告
curl -s "https://jwc.usc.edu.cn/wzsy/sy/tzgg.htm" | grep -i "推免\|保研\|推荐免试"
# 研究生院招生信息
curl -s "https://yjs.usc.edu.cn/zsxx/sszs.htm" | grep -i "推免"
# 如果首页没有，搜索更多分页
```

### Step 2：找到具体文章
```bash
# 从通知列表提取链接
curl -s "https://jwc.usc.edu.cn/wzsy/sy/tzgg.htm" | \
  grep -oP '<a[^>]*href="[^"]*info/[^"]*"[^>]*>[^<]*推免[^<]*</a>'
# 或直接搜索
curl -s "https://www.bing.com/search?q=site:usc.edu.cn+推免+名单+2026届"
```

### Step 3：提取内容
```python
import requests
from bs4 import BeautifulSoup

resp = requests.get(article_url, headers={"User-Agent": "Mozilla/5.0..."})
resp.encoding = resp.apparent_encoding  # 处理 GBK/UTF-8
soup = BeautifulSoup(resp.text, "lxml")

# 正文通常在特定 div 中
content = soup.select_one(".v_news_content") or soup.select_one("#vsb_content")
# 提取表格（名单通常是表格）
tables = content.find_all("table") if content else []
# 或提取附件链接
attachments = content.find_all("a", href=True) if content else []
```

### Step 4：处理附件
```python
# 如果是 .doc/.xls 附件
for a in attachments:
    href = urljoin(base_url, a["href"])
    if href.endswith((".doc", ".xls", ".pdf")):
        download_large(href, f"data/{a.get_text(strip=True)}")
```

### Step 5：结构化输出
```python
# 如果是 HTML 表格
import pandas as pd
dfs = pd.read_html(str(content))
df = dfs[0]  # 第一个表格
df.to_csv("推免名单.csv", index=False, encoding="utf-8-sig")
```

### 注意事项
```
• 公示期通常 7~15 天，过期可能下架
• 如果找不到，尝试：
  - 搜索引擎缓存（cache:usc.edu.cn/xxx）
  - web.archive.org 历史快照
  - 各学院官网（可能分院公示）
• 编码问题：老站可能 GBK，resp.encoding = 'gbk'
• 附件可能需要登录/校内 IP 才能下载
```

---

## 六十一、多语言/国际化爬取

### 处理多语言网站
```python
# 1. 通过 Accept-Language 头控制返回语言
headers = {"Accept-Language": "zh-CN,zh;q=0.9,en;q=0.8"}

# 2. 通过 URL 路径/参数切换语言
# /en/products, /zh/products, ?lang=zh, ?hl=zh-CN

# 3. 通过 Cookie 切换
session.cookies.set("locale", "zh_CN")
session.cookies.set("lang", "zh")

# 4. 检测当前语言
import re
html_lang = soup.html.get("lang", "")  # <html lang="zh-CN">
meta_lang = soup.find("meta", attrs={"http-equiv": "Content-Language"})
```

### 多编码处理
```python
# 自动检测并处理混合编码
def smart_decode(resp):
    # 优先用 HTTP 头声明
    if resp.encoding and resp.encoding.lower() != 'iso-8859-1':
        return resp.text
    # 其次用 meta 标签
    content = resp.content
    meta_match = re.search(rb'charset=["\']?([^"\'\s;>]+)', content[:2048])
    if meta_match:
        encoding = meta_match.group(1).decode()
        return content.decode(encoding, errors='replace')
    # 最后用 chardet
    import chardet
    detected = chardet.detect(content)
    return content.decode(detected['encoding'] or 'utf-8', errors='replace')
```

---

## 六十二、移动端 APP API 爬取

很多数据只在移动端 APP 中提供，没有 Web 版。此时需要逆向 APP 的 API。

### 工具链
| 工具 | 平台 | 用途 |
|------|------|------|
| **mitmproxy** | 通用 | HTTPS 中间人代理，拦截/修改/重放请求 |
| **Charles Proxy** | macOS/Windows | GUI 抓包工具（付费） |
| **Fiddler** | Windows | 免费抓包工具 |
| **HttpCanary** | Android | 手机端抓包 APP |
| **Frida** | 通用 | 动态 Hook 框架，修改 APP 运行时行为 |
| **JADX** | 通用 | APK 反编译，查看 Java/Kotlin 源码 |
| **apktool** | 通用 | APK 解包/重打包 |

### 抓包流程
```
1. 安装 mitmproxy / Charles / Fiddler
2. 手机设置代理指向电脑 IP + 端口
3. 安装 mitmproxy CA 证书到手机（信任根证书）
4. 打开 APP 操作，在电脑上查看所有请求
5. 找到目标 API 端点和参数格式
6. 分析请求头中的鉴权参数（token/sign/timestamp）
7. 用 Python requests/tls-client 复现请求
```

### Android 7+ 证书信任问题
```
Android 7+ 默认不信任用户安装的 CA 证书。

解决方案（按难度排序）：
1. 使用 Android 6 或更低版本的模拟器/设备
2. 使用 Xposed + JustTrustMe 模块（禁用证书验证）
3. 使用 Frida Hook 禁用 SSL Pinning
4. 反编译 APK 修改 network_security_config.xml
5. 使用 root 设备将证书安装到系统目录
```

### Frida 禁用 SSL Pinning
```javascript
// frida-ssl-unpinning.js
// 通用 SSL Pinning 绕过脚本
Java.perform(function() {
    // OkHttp3
    var OkHttpClient = Java.use("okhttp3.OkHttpClient");
    var CertificatePinner = Java.use("okhttp3.CertificatePinner");
    CertificatePinner.check.overload('java.lang.String', 'java.util.List').implementation = function(hostname, peerCertificates) {
        console.log("Bypassing SSL pinning for: " + hostname);
        return;
    };
    
    // TrustManagerImpl
    var TrustManagerImpl = Java.use("com.android.org.conscrypt.TrustManagerImpl");
    TrustManagerImpl.verifyChain.implementation = function() {
        console.log("Bypassing TrustManagerImpl");
        return arguments[0];
    };
});

// 使用：frida -U -f com.target.app -l frida-ssl-unpinning.js
```

### 常见移动端签名算法
```python
# 典型签名结构
import hashlib, time, json

def make_mobile_sign(params: dict, app_secret: str) -> dict:
    """模拟移动端签名"""
    timestamp = str(int(time.time()))
    nonce = hashlib.md5(str(time.time()).encode()).hexdigest()[:16]
    
    # 参数排序拼接
    sorted_params = sorted(params.items())
    sign_str = "&".join(f"{k}={v}" for k, v in sorted_params)
    sign_str += f"&timestamp={timestamp}&nonce={nonce}&key={app_secret}"
    
    sign = hashlib.md5(sign_str.encode()).hexdigest()
    
    params.update({
        "timestamp": timestamp,
        "nonce": nonce,
        "sign": sign,
    })
    return params

# 请求头通常还需要
headers = {
    "User-Agent": "okhttp/4.9.3",  # 或 okhttp/3.x
    "App-Version": "5.2.1",
    "Device-Id": "android_abc123",
    "Platform": "android",
    "OS-Version": "13",
}
```

### 模拟器方案
```bash
# 使用 Android 模拟器（无需真机）
# 方案1：Android Studio AVD（官方，最兼容）
# 方案2：Genymotion（性能好，支持 root）
# 方案3：夜神/雷电/MuMu（国产，自带 root）

# 设置代理
adb shell settings put global http_proxy 192.168.1.100:8080

# 安装 CA 证书
adb push mitmproxy-ca-cert.cer /sdcard/
# 然后在手机设置中安装

# Frida 连接
frida-ps -U  # 列出进程
frida -U -f com.target.app -l script.js
```

---

## 六十三、登录与认证流程爬取

很多数据需要登录后才能访问。安全地处理登录是爬虫的核心技能之一。

### 认证类型与方案
| 认证方式 | 特点 | 方案 |
|----------|------|------|
| Cookie/Session | 传统登录，表单提交 | Playwright 模拟登录 → 保存 cookie |
| JWT Token | API 返回 token，放在 Authorization 头 | 调用登录 API → 提取 token → 复用 |
| OAuth 2.0 | 授权码/客户端凭证 | 获取 access_token → 带 Bearer 头请求 |
| 短信/邮箱验证码 | 需要人工或打码平台 | 手动获取后注入 |
| 扫码登录 | 微信/支付宝扫码 | Playwright 截图 → 手机扫码 → 保存 cookie |
| 2FA (TOTP) | Google Authenticator 等 | pyotp 本地生成验证码 |
| API Key | 开放平台 | 直接带 key 请求 |

### Cookie 登录（Playwright）
```python
from playwright.sync_api import sync_playwright
import json

def login_and_save_cookies(url, username, password, cookie_path="cookies.json"):
    """登录并保存 cookie 供后续复用"""
    with sync_playwright() as p:
        browser = p.chromium.launch(headless=False)
        page = browser.new_page()
        
        # 1. 导航到登录页
        page.goto(url)
        
        # 2. 填写表单
        page.fill('input[name="username"], input[type="email"]', username)
        page.fill('input[name="password"], input[type="password"]', password)
        
        # 3. 点击登录
        page.click('button[type="submit"]')
        
        # 4. 等待登录完成（检测 URL 变化或特定元素出现）
        page.wait_for_url("**/dashboard**", timeout=30000)
        
        # 5. 保存所有 cookie
        cookies = page.context.cookies()
        with open(cookie_path, "w") as f:
            json.dump(cookies, f, indent=2)
        
        browser.close()
    return cookies

def load_cookies(context, cookie_path="cookies.json"):
    """加载已保存的 cookie"""
    with open(cookie_path) as f:
        cookies = json.load(f)
    context.add_cookies(cookies)

# 使用
login_and_save_cookies("https://target.com/login", "user", "pass")
# 后续爬取时直接加载 cookie，无需重新登录
```

### JWT Token 登录
```python
import requests, time

class JWTAuth:
    """JWT Token 认证管理器"""
    
    def __init__(self, login_url, username, password):
        self.login_url = login_url
        self.username = username
        self.password = password
        self.access_token = None
        self.refresh_token = None
        self.expires_at = 0
    
    def login(self):
        """登录获取 token"""
        resp = requests.post(self.login_url, json={
            "username": self.username,
            "password": self.password,
        })
        data = resp.json()
        self.access_token = data["access_token"]
        self.refresh_token = data.get("refresh_token")
        self.expires_at = time.time() + data.get("expires_in", 3600) - 60  # 提前 60s 刷新
    
    def refresh(self):
        """刷新 token"""
        if not self.refresh_token:
            self.login()
            return
        resp = requests.post(self.login_url + "/refresh", json={
            "refresh_token": self.refresh_token,
        })
        data = resp.json()
        self.access_token = data["access_token"]
        self.expires_at = time.time() + data.get("expires_in", 3600) - 60
    
    def get_headers(self):
        """获取认证头（自动刷新）"""
        if time.time() >= self.expires_at:
            self.refresh()
        return {"Authorization": f"Bearer {self.access_token}"}

# 使用
auth = JWTAuth("https://api.target.com/auth/login", "user", "pass")
auth.login()
resp = requests.get("https://api.target.com/data", headers=auth.get_headers())
```

### 2FA (TOTP) 自动处理
```python
import pyotp, time

# 设置 TOTP（从 APP 扫码时获取的密钥）
totp = pyotp.TOTP("JBSWY3DPEHPK3PXP")  # base32 编码的密钥

# 生成当前验证码
code = totp.now()  # "123456"
print(f"当前验证码: {code}")

# 在登录流程中使用
page.fill('input[name="otp"]', totp.now())
page.click('button[type="submit"]')
```

### OAuth 2.0 客户端凭证模式
```python
import requests

def get_oauth_token(token_url, client_id, client_secret):
    """OAuth 2.0 Client Credentials 模式"""
    resp = requests.post(token_url, data={
        "grant_type": "client_credentials",
        "client_id": client_id,
        "client_secret": client_secret,
    })
    return resp.json()["access_token"]

token = get_oauth_token(
    "https://api.target.com/oauth/token",
    "my_client_id",
    "my_client_secret"
)
resp = requests.get("https://api.target.com/data", headers={
    "Authorization": f"Bearer {token}"
})
```

### Cookie 过期检测与自动续期
```python
import json, time
from pathlib import Path

class CookieManager:
    """Cookie 生命周期管理"""
    
    def __init__(self, cookie_path="cookies.json", max_age_hours=24):
        self.cookie_path = Path(cookie_path)
        self.max_age = max_age_hours * 3600
    
    def is_valid(self):
        """检查 cookie 是否仍然有效"""
        if not self.cookie_path.exists():
            return False
        age = time.time() - self.cookie_path.stat().st_mtime
        return age < self.max_age
    
    def load(self):
        """加载 cookie"""
        with open(self.cookie_path) as f:
            return json.load(f)
    
    def save(self, cookies):
        """保存 cookie"""
        with open(self.cookie_path, "w") as f:
            json.dump(cookies, f, indent=2)
    
    def get_or_refresh(self, login_func):
        """获取有效 cookie，过期则重新登录"""
        if self.is_valid():
            return self.load()
        cookies = login_func()  # 传入登录函数
        self.save(cookies)
        return cookies
```

### 扫码登录自动化
```python
from playwright.sync_api import sync_playwright

def qr_code_login(url, timeout=120):
    """扫码登录：截图二维码 → 手机扫码 → 等待登录成功"""
    with sync_playwright() as p:
        browser = p.chromium.launch(headless=False)
        page = browser.new_page()
        page.goto(url)
        
        # 截图二维码
        qr_element = page.locator("img.qrcode, canvas.qrcode, .qr-image")
        qr_element.screenshot(path="qr_code.png")
        print("请用手机扫描二维码图片: qr_code.png")
        
        # 等待登录成功
        page.wait_for_url("**/home**", timeout=timeout * 1000)
        
        # 保存 cookie
        cookies = page.context.cookies()
        browser.close()
        return cookies
```

---

## 六十四、验证码规避策略

比解决验证码更好的方法是**不触发验证码**。

### 验证码触发因素（按权重排序）
```
1. IP 信誉（30%）— 数据中心 IP、黑名单 IP、异常地理
2. 请求频率（25%）— 短时间内大量请求
3. 浏览器指纹（20%）— 异常/不一致的指纹
4. 行为模式（15%）— 机械化的鼠标/键盘操作
5. 会话特征（10%）— 无 cookie、无历史记录、首次访问
```

### IP 层面规避
```python
# ✅ 使用住宅代理（不是数据中心）
# ✅ 每个任务使用不同的出口 IP
# ✅ geoip=True 让时区/语言匹配 IP 位置
# ✅ 同一任务保持同一 IP（粘性会话）
# ✅ 避免使用被大量人用过的 IP（共享代理）
# ❌ 不要从同一 IP 访问多个目标站
# ❌ 不要使用已知被标记的 IP 段
```

### 频率层面规避
```python
import random, time

class StealthDelayer:
    """隐身延迟器：模拟真人浏览节奏"""
    
    def __init__(self):
        self.last_request_time = 0
        self.request_count = 0
    
    def wait(self):
        """人类化的请求间隔"""
        self.request_count += 1
        
        # 基础延迟：2~8s（正态分布，均值 4s）
        base = random.gauss(4, 1.5)
        base = max(2, min(8, base))
        
        # 每 20 个请求后休息更久（模拟阅读疲劳）
        if self.request_count % 20 == 0:
            base += random.uniform(10, 30)
        
        # 每 100 个请求后长休息（模拟离开）
        if self.request_count % 100 == 0:
            base += random.uniform(60, 180)
        
        time_since_last = time.time() - self.last_request_time
        remaining = max(0, base - time_since_last)
        if remaining > 0:
            time.sleep(remaining)
        
        self.last_request_time = time.time()

delayer = StealthDelayer()
# 每次请求前调用
delayer.wait()
resp = session.get(url)
```

### 浏览器指纹层面规避
```python
# ✅ 指纹内部一致性（UA + GPU + 屏幕 必须匹配同一设备）
# ✅ 使用 CloakBrowser/Camoufox 的确定性指纹种子
# ✅ 保持同一指纹用于同一会话
# ✅ viewport < screen（真人有书签栏/标签栏）
# ❌ 不要用 JS 注入方式伪装指纹（可被检测）
# ❌ 不要每次请求都换指纹（不自然）
# ❌ 不要噪声注入（高级检测能识别）
```

### 行为层面规避
```python
# 模拟真实用户行为模式
def human_like_browse(page, target_url):
    """模拟真人浏览行为"""
    
    # 1. 先到首页（不要直接跳目标页）
    page.goto("https://target.com")
    time.sleep(random.uniform(2, 5))
    
    # 2. 随机滚动
    for _ in range(random.randint(1, 3)):
        scroll_amount = random.randint(200, 600)
        page.mouse.wheel(0, scroll_amount)
        time.sleep(random.uniform(0.5, 2))
    
    # 3. 导航到目标页（通过链接或搜索）
    page.goto(target_url)
    time.sleep(random.uniform(3, 8))
    
    # 4. 阅读内容（随机滚动）
    for _ in range(random.randint(2, 5)):
        scroll_amount = random.randint(300, 800)
        page.mouse.wheel(0, scroll_amount)
        time.sleep(random.uniform(1, 4))
        # 偶尔回滚
        if random.random() < 0.15:
            page.mouse.wheel(0, -random.randint(100, 300))
            time.sleep(0.5)
```

### 会话预热（Cookie Warming）
```python
# 新 IP/新指纹先"预热"再正式爬取
def warm_up_session(page, domain):
    """会话预热：建立正常访问历史"""
    
    # 1. 访问首页
    page.goto(f"https://{domain}")
    time.sleep(random.uniform(3, 8))
    
    # 2. 浏览几个无关页面（模拟正常浏览）
    warm_pages = ["/about", "/contact", "/blog"]
    for path in random.sample(warm_pages, k=random.randint(1, 2)):
        try:
            page.goto(f"https://{domain}{path}")
            time.sleep(random.uniform(2, 5))
        except:
            pass
    
    # 3. 回到首页
    page.goto(f"https://{domain}")
    time.sleep(random.uniform(2, 4))
    
    # 现在 cookie 已建立，可以开始正式爬取
    return page.context.cookies()
```

### 验证码检测与降级
```python
def detect_captcha(page) -> str:
    """检测是否出现验证码"""
    captcha_indicators = {
        "recaptcha": [".g-recaptcha", "#recaptcha", "iframe[src*='recaptcha']"],
        "hcaptcha": [".h-captcha", "iframe[src*='hcaptcha']"],
        "turnstile": ["[src*='turnstile']", "[src*='challenges.cloudflare']"],
        "funcaptcha": [".funcaptcha", "iframe[src*='funcaptcha']"],
        "geetest": [".geetest", ".geetest_panel"],
    }
    
    for captcha_type, selectors in captcha_indicators.items():
        for selector in selectors:
            if page.locator(selector).count() > 0:
                return captcha_type
    return None

# 使用：检测到验证码时切换策略
captcha = detect_captcha(page)
if captcha:
    print(f"检测到验证码: {captcha}")
    # 策略1：换 IP（代理轮换）
    # 策略2：使用验证码解决服务
    # 策略3：降低频率等待一段时间
    # 策略4：切换到移动端 API（通常不需要验证码）
```

### 终极规避：找不需要验证码的入口
```
优先级（从高到低）：
1. 公开 API / RSS / Sitemap → 完全不需要验证码
2. 移动端 API → 通常不需要验证码（鉴权靠 token 不靠 IP）
3. GraphQL 端点 → 有时比 REST API 限制更松
4. 第三方数据聚合 → 如 Google Cache、Archive.org
5. 低峰时段访问 → 凌晨反爬可能更松
6. 带验证码的 Web 页面 → 最后手段
```

---

## 六十五、爬虫安全与防护

### 保护你的爬虫基础设施
```
• 代理账号密码不要硬编码在代码中 → 用环境变量/.env
• 爬取的数据如果含敏感信息 → 加密存储
• 爬虫服务器不要暴露公网 → 防火墙限制
• 日志中不要记录完整 cookie/token → 脱敏处理
• 定期清理本地缓存的 cookie 文件
```

### 避免被反溯源
```
• 不要在爬虫中暴露真实邮箱/手机号
• WHOIS 隐私保护（如果爬取需要注册）
• 使用独立机器/容器跑爬虫，与个人环境隔离
• 代理出口 IP 不要关联到你的真实身份
```

### .env 环境变量模板
```bash
# .env — 绝不提交到 Git！加入 .gitignore
# 代理配置
PROXY_POOL_URL=http://proxy-service:port
PROXY_USERNAME=user
PROXY_PASSWORD=pass123

# 验证码 API
TWOCAPTCHA_API_KEY=xxxxx
ANTICAPTCHA_API_KEY=xxxxx

# 目标站登录凭证
TARGET_EMAIL=bot@example.com
TARGET_PASSWORD=xxxxx

# 数据库
DB_HOST=localhost
DB_PORT=5432
DB_NAME=scrape_data
DB_USER=scraper
DB_PASS=xxxxx

# 告警
DINGTALK_WEBHOOK=https://oapi.dingtalk.com/robot/send?access_token=xxx
```

### Docker 容器隔离
```dockerfile
# 用独立容器跑爬虫，与宿主机环境隔离
FROM python:3.12-slim

RUN apt-get update && apt-get install -y \
    fonts-noto-color-emoji fonts-wqy-zenhei xvfb

WORKDIR /app
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

COPY . .

# 非 root 用户运行
RUN useradd -m spider && chown -R spider:spider /app
USER spider

CMD ["python", "main.py"]
```yaml
# docker-compose.yml — 网络隔离
services:
  spider:
    build: .
    networks:
      - spider-net    # 独立网络，不暴露宿主机
    env_file: .env
    volumes:
      - ./data:/app/data
    restart: on-failure

  redis:
    image: redis:alpine
    networks:
      - spider-net

networks:
  spider-net:
    driver: bridge
    internal: true  # 不可访问外部网络（可选）
```

### 日志脱敏
```python
import re, logging

class SensitiveFilter(logging.Filter):
    """自动脱敏日志中的敏感信息"""
    patterns = [
        (r'(password|passwd|pwd|token|api_key|secret)["\s:=]+["\']?(\S+)', r'\1=***REDACTED***'),
        (r'Bearer\s+\S+', 'Bearer ***REDACTED***'),
        (r'cookie["\s:=]+["\']?([^"\']+)', 'cookie=***REDACTED***'),
        (r'\d{11}', '***PHONE***'),  # 手机号
        (r'\d{17}[\dXx]', '***IDCARD***'),  # 身份证
    ]
    
    def filter(self, record):
        msg = record.getMessage()
        for pattern, replacement in self.patterns:
            msg = re.sub(pattern, replacement, msg, flags=re.IGNORECASE)
        record.msg = msg
        return True

# 使用
handler = logging.StreamHandler()
handler.addFilter(SensitiveFilter())
logger = logging.getLogger("spider")
logger.addHandler(handler)
```

### 数据加密存储
```python
from cryptography.fernet import Fernet

# 生成密钥（保存好，丢失无法恢复）
key = Fernet.generate_key()
cipher = Fernet(key)

# 加密敏感数据
def encrypt_data(data: str) -> bytes:
    return cipher.encrypt(data.encode())

def decrypt_data(encrypted: bytes) -> str:
    return cipher.decrypt(encrypted).decode()

# 示例：加密存储 cookie
with open("cookies.enc", "wb") as f:
    f.write(encrypt_data(json.dumps(session_cookies)))

# 读取时解密
with open("cookies.enc", "rb") as f:
    cookies = json.loads(decrypt_data(f.read()))
```

### 法律防护
```
• 保留完整的爬取日志（URL、时间、状态码）→ 证明行为合理
• 记录 robots.txt 遵守情况 → 证明善意
• 不要爬取需要登录才能看的私密数据
• 不要绕过付费墙/付费内容的技术保护
• 商业使用建议咨询律师
• 学术/个人研究通常风险较低
```

---

## 六十六、总结：技术栈全景图

```
┌─────────────────────────────────────────────────────────────────┐
│                        爬虫技术全景                               │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  发现层：sitemap / RSS / 搜索引擎 / BFS / AI Agent              │
│                                                                 │
│  请求层：curl → requests → tls-client → Playwright              │
│          → Patchright → CloakBrowser → Camoufox                 │
│                                                                 │
│  反检测：UA/头 → TLS指纹 → JS环境 → 浏览器指纹                  │
│          → 行为分析 → 验证码 → 设备一致性                        │
│                                                                 │
│  解析层：grep → BS4 → lxml → trafilatura → AI 提取             │
│                                                                 │
│  数据层：JSON/CSV → SQLite → PostgreSQL → ES → 数据湖           │
│                                                                 │
│  调度层：cron → Celery → Airflow → Crawlab/Gerapy               │
│                                                                 │
│  监控层：日志 → 健康检查 → 告警 → Grafana                       │
│                                                                 │
│  辅助层：代理池 / OCR / 截图 / 去重 / 增量 / 断点续爬           │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

---

## 六十七、铁律（所有爬虫必须遵守）

1. **robots.txt 优先**：爬任何路径前先解析并遵守
2. **限速**：crawl-delay 默认 1s，未指定也至少 1s
3. **表明身份**：User-Agent 写清楚是谁在爬
4. **不过载**：对目标服务器负责，宁可慢不可崩
5. **不存敏感数据**：个人隐私信息不采集
6. **合法用途**：不绕过付费墙，不破解加密接口商用
7. **可溯源**：记录数据来源 URL 和爬取时间
8. **及时停止**：收到 403/429/法律函件立即停止

---

*指南版本：v4-final | 最后更新：2026-07-23 | 总计 68 节 | 约 150KB*
*整合来源：mindrally/web-scraping、crawl4ai-skill、algo-seo-crawl、spider-ops、Crawlee、CloakBrowser、Camoufox、Patchright、Botright、Scrapling、Trawl、browser-use、Crawl4AI、Firecrawl + GitHub 生态调研 + 实战经验*

---

## 六十八、GitHub 深挖：新兴爬虫技术与工具（2026）

### 68.1 高级代理策略：创新 IP 轮换方案

#### AWS API Gateway 代理池（requests-ip-rotator ⭐1669）
```python
# pip install requests-ip-rotator
from requests_ip_rotator import ApiGateway

# 利用 AWS API Gateway 的无限 IP 池
# 每次请求都会从不同的 AWS IP 发出
gateway = ApiGateway("https://target.com")
gateway.start()

session = requests.Session()
session.mount("https://target.com", gateway)

# 现在每次请求都使用不同的 IP
for i in range(1000):
    response = session.get("https://target.com/api/data")
    # AWS 自动轮换 IP，几乎不可能被封
```

**优势**：
- 伪无限 IP 池（AWS 有数百万个 IP）
- 无需购买代理，只需 AWS 账号
- 适合暴力破解和高频爬取

**限制**：
- 需要 AWS 账号和 API Gateway 配置
- 有一定延迟（经过 AWS 转发）
- 成本取决于请求量

#### AWS Lambda 代理池（lambda-scraper）
```python
# 将爬虫部署为 Lambda 函数
# 每次调用都是全新的 IP + 全新的环境
import boto3
import json

lambda_client = boto3.client('lambda')

def scrape_with_lambda(url):
    response = lambda_client.invoke(
        FunctionName='my-scraper',
        Payload=json.dumps({'url': url})
    )
    return json.loads(response['Payload'].read())

# 并发调用多个 Lambda 实例
with ThreadPoolExecutor(max_workers=50) as pool:
    results = list(pool.map(scrape_with_lambda, urls))
```

**优势**：
- 完全无状态，每次都是全新环境
- 自动扩展，无需管理代理
- 按需付费，成本低

### 68.2 社交媒体爬虫（无 API Key 方案）

#### Twitter/X 爬虫（Scweet ⭐1569）
```python
# pip install scweet
from scweet.scweet import Scweet

scraper = Scweet()
# 无需 API Key，智能多账号池化
tweets = scraper.get_tweets(
    queries=["python programming", "web scraping"],
    since="2024-01-01",
    until="2024-12-31",
    lang="en"
)

# 获取用户信息和关注者
user_info = scraper.get_user_info("elonmusk")
followers = scraper.get_followers("elonmusk", count=1000)
```

**特点**：
- 无需 Twitter API Key
- 智能多账号轮换
- 支持代理和异步
- 绕过速率限制

#### Instagram 机器人（GramAddict ⭐1590）
```bash
# pip install gramaddict
# 基于 UIAutomator2，模拟真实用户行为

gramaddict --device YOUR_DEVICE_ID \
  --username your_account \
  --interact-hashtag photography \
  --likes-count 1-3 \
  --stories-count 1-5
```

**用途**：
- 自动化互动（点赞、评论、关注）
- 数据收集（帖子、故事、粉丝列表）
- 账号增长策略

#### LinkedIn 爬虫（linkedin-profile-scraper-api ⭐769）
```python
# 结构化 LinkedIn 数据提取
import requests

# 使用开源 API 或自建服务
profile_data = {
    "name": "John Doe",
    "title": "Software Engineer",
    "company": "Tech Corp",
    "location": "San Francisco, CA",
    "experience": [...],
    "education": [...],
    "skills": [...]
}
```

#### YouTube 评论爬取（youtube-comment-suite ⭐315）
```python
# 批量下载视频评论，无需 YouTube API
# 支持视频、播放列表、频道级别爬取
# 导出为 JSON/CSV/SQLite
```

#### Google Maps 数据爬取（google-maps-scraper ⭐5217）
```python
# Go 语言实现，高性能
# 提取：名称、地址、电话、网站、评分、评论数、经纬度等

# 使用示例（通过命令行）
./google-maps-scraper \
  --search "restaurants in San Francisco" \
  --output results.json \
  --max-results 1000
```

### 68.3 垂直领域爬虫

#### Amazon 产品数据（amazon-scraper ⭐437）
```python
# 提取产品数据：名称、价格、评分、评论、图片等
# 处理 Amazon 的反爬机制
# 支持批量 ASIN 查询
```

#### 房地产数据（HomeHarvest ⭐715）
```python
# pip install homeharvest
from homeharvest import scrape_property

# 从多个房产网站提取数据
properties = scrape_property(
    location="San Francisco, CA",
    listing_type="for_sale",  # or "sold", "for_rent"
    property_type=["single_family", "condo"],
    past_days=30
)

# 返回结构化数据：价格、面积、卧室数、地址等
```

### 68.4 开源反检测浏览器方案

#### Undetectable Fingerprint Browser（⭐824）
```python
# 免费的 Multilogin/Incogniton/Kameleo 替代品
# 支持 Canvas/WebGL/User-Agent 指纹欺骗

from undetectable_browser import Browser

browser = Browser(
    profile_id="profile_001",
    fingerprint={
        "user_agent": "Mozilla/5.0 ...",
        "canvas_noise": True,
        "webgl_vendor": "Intel Inc.",
        "webgl_renderer": "Intel Iris OpenGL Engine",
        "timezone": "America/New_York",
        "language": "en-US"
    }
)

# 与 Selenium/Playwright 集成
page = browser.new_page()
page.goto("https://target.com")
```

**特点**：
- 完全开源免费
- 支持多账号管理
- 与主流自动化工具集成
- 适合社交媒体多账号操作

#### Fortress 隐身 Chromium（⭐395）
```python
# 专为爬虫设计的隐身 Chromium 引擎
# 自动处理常见的检测点

from fortress import StealthBrowser

browser = StealthBrowser(
    headless=True,
    stealth_level="high"  # 自动应用所有隐身补丁
)
```

#### Mochi 高保真指纹（⭐216）
```python
# 高保真浏览器指纹库
# 专注于商业级自动化

from mochi import FingerprintBrowser

# 使用真实设备指纹数据
browser = FingerprintBrowser(
    device_profile="macbook_pro_m1",
    location="new_york"
)
```

### 68.5 Go 语言爬虫方案

#### HTTPCloak（⭐1173）
```go
// Go HTTP 客户端，浏览器级别 TLS/HTTP2 指纹
// 完美模拟 Chrome/Firefox/Safari 的加密层特征

package main

import (
    "github.com/sardanioss/httpcloak"
    "fmt"
)

func main() {
    client := httpcloak.NewClient(
        httpcloak.WithBrowser(httpcloak.Chrome),
        httpcloak.WithHTTP2(),
    )
    
    resp, err := client.Get("https://target.com")
    if err != nil {
        panic(err)
    }
    defer resp.Body.Close()
    
    // JA3/JA4 指纹与真实 Chrome 完全一致
    // 绕过 Akamai、Cloudflare 等检测
    fmt.Println(resp.StatusCode)
}
```

**优势**：
- 高性能（Go 语言原生）
- 完美的 TLS 指纹伪装
- 支持 HTTP/1.1、HTTP/2、HTTP/3
- 适合大规模并发爬取

### 68.6 浏览器自动化工具基准测试

#### Browsers Benchmark（⭐358）
```python
# 测试各种浏览器自动化工具的绕过率、性能、隐身性
# 对比：Cloudflare、DataDome、reCAPTCHA、Kasada、Imperva、Akamai 等

# 测试维度：
# 1. 绕过率（Bypass Rate）
# 2. 检测率（Detection Rate）
# 3. 性能（请求/秒）
# 4. 内存占用
# 5. CPU 使用率

# 测试工具：
# - Playwright (原版)
# - Patchright (反检测版)
# - Selenium
# - Puppeteer
# - CloakBrowser
# - Camoufox
# - 各种隐身浏览器

# 最新测试结果摘要：
# - Cloudflare Turnstile: CloakBrowser 98%, Patchright 95%, Playwright 60%
# - DataDome: CloakBrowser 92%, Camoufox 88%, Playwright 45%
# - reCAPTCHA v3: CloakBrowser 95%, Botright 90%, Playwright 70%
# - Kasada: CloakBrowser Pro 90%, 其他 < 50%
```

**用途**：
- 选择最适合目标网站的工具
- 评估不同方案的成本效益
- 持续监控工具的有效性

### 68.7 CDP 协议补丁（CDP-Patches ⭐163）

#### OS 级别 CDP 泄漏修复
```python
# Chrome DevTools Protocol (CDP) 会被反爬系统检测
# 这个库在操作系统级别修补 CDP 泄漏

# 与 Playwright 集成
from cdp_patches import apply_patches

# 在启动浏览器前应用补丁
apply_patches()

# 现在 Playwright 的 CDP 连接对检测系统不可见
from playwright.sync_api import sync_playwright
with sync_playwright() as p:
    browser = p.chromium.launch()
    page = browser.new_page()
    # 检测系统无法发现这是自动化浏览器
```

**修补的泄漏**：
- `navigator.webdriver` 属性
- CDP 相关的 WebSocket 连接
- 自动化相关的 JavaScript 对象
- 浏览器控制台命令历史

### 68.8 新兴趋势总结

| 趋势 | 代表项目 | 影响 |
|------|---------|------|
| 云原生代理 | requests-ip-rotator, lambda-scraper | 无限 IP 池，按需扩展 |
| 无 API 社交媒体爬取 | Scweet, GramAddict | 绕过官方 API 限制 |
| 开源反检测浏览器 | undetectable-fingerprint-browser | 降低多账号管理成本 |
| Go 语言高性能爬虫 | HTTPCloak | 大规模并发，完美指纹 |
| 基准测试驱动决策 | browsers-benchmark | 数据驱动的工具选择 |
| 系统级反检测 | CDP-Patches | 更深层的隐身技术 |

---

## 六十九、多语言爬虫生态全景

除了 Python，许多语言都有优秀的爬虫框架。选择合适的语言可以获得更好的性能或特定的功能。

### Go 语言（高性能首选）

#### Colly ⭐25383
```go
// 特点：极速（>1000 req/s）、简洁 API、自动 cookie 管理
// 适用：大规模并发爬取、数据采集

import "github.com/gocolly/colly/v2"

func main() {
    c := colly.NewCollector(
        colly.UserAgent("Mozilla/5.0 ..."),
        colly.MaxDepth(3),
        colly.Async(true),  // 异步模式
    )
    
    // 限制并发和延迟
    c.Limit(&colly.LimitRule{
        DomainGlob:  "*",
        Parallelism: 10,
        Delay:       1 * time.Second,
    })
    
    // 提取数据
    c.OnHTML("div.product", func(e *colly.HTMLElement) {
        product := map[string]string{
            "title": e.ChildText("h2"),
            "price": e.ChildText(".price"),
        }
        fmt.Println(product)
    })
    
    // 跟踪链接
    c.OnHTML("a[href]", func(e *colly.HTMLElement) {
        e.Request.Visit(e.Attr("href"))
    })
    
    c.Visit("https://example.com")
    c.Wait()  // 等待所有异步任务完成
}
```

#### Geziyor ⭐2773
```go
// 特点：8748 req/s（MacBook Pro 基准）、JS 渲染、自动数据导出
// 适用：需要 JS 渲染的高速爬取

geziyor.NewGeziyor(&geziyor.Options{
    StartURLs: []string{"https://example.com"},
    ParseFunc: func(g *geziyor.Geziyor, r *client.Response) {
        r.HTMLDoc.Find("div.item").Each(func(_ int, s *goquery.Selection) {
            g.Exports <- map[string]interface{}{
                "title":  s.Find("h2").Text(),
                "link":   s.Find("a").AttrOr("href", ""),
            }
        })
        // 自动翻页
        if href, ok := r.HTMLDoc.Find("a.next").Attr("href"); ok {
            g.Get(r.JoinURL(href), g.Opt.ParseFunc)
        }
    },
    Exporters: []export.Exporter{&export.JSON{}},  // 自动导出 JSON
    // JS 渲染（需要安装 Chrome）
    // BrowserEndpoint: "ws://localhost:9222",
}).Start()
```

#### 其他 Go 框架
| 框架 | 特点 |
|------|------|
| pholcus | 分布式、高并发 |
| ferret | 声明式网页爬取 |
| hakrawler | 快速发现 Web 应用端点 |

### Rust（极致性能）
```rust
// spider - 最快的爬虫和索引器
use spider::website::Website;

#[tokio::main]
async fn main() {
    let mut website = Website::new("https://example.com");
    website.crawl().await;
    
    for page in website.get_pages().unwrap().iter() {
        println!("URL: {}", page.get_url());
        println!("HTML: {}", page.get_html());
    }
}
```

### Java（企业级）
| 框架 | 特点 | 适用场景 |
|------|------|----------|
| Apache Nutch | 高度可扩展、生产级 | 大规模企业爬虫 |
| crawler4j | 简单易用 | 快速原型开发 |
| WebMagic | 可扩展框架 | 中等规模项目 |
| Heritrix3 | 存档级爬虫 | 网页归档、图书馆 |
| StormCrawler | 基于 Storm | 低延迟、高吞吐 |

### C#（.NET 生态）
```csharp
// DotnetSpider - 跨平台爬虫框架
// Abot - 快速灵活的爬虫
// SkyScraper - 异步爬虫 + Reactive Extensions
```

### PHP
| 框架 | 特点 |
|------|------|
| QueryList ⭐2697 | 渐进式 PHP 爬虫框架 |
| Goutte | 简单的屏幕抓取库 |
| spatie/crawler | 支持 JavaScript 执行 |

### 其他语言
- **Ruby**: Nokogiri（HTML 解析）、Mechanize（自动化交互）
- **Elixir**: Crawly ⭐1109（高性能 Elixir 爬虫）
- **Perl**: web-scraper（HTML/CSS 选择器）
- **R**: rvest（简单的 Web 爬取）
- **Scala**: scrala（受 Scrapy 启发）

### 语言选择指南
| 场景 | 推荐语言 | 理由 |
|------|----------|------|
| 快速原型 | Python | 开发速度快、生态丰富 |
| 大规模并发 | Go | 高并发、低内存占用 |
| 极致性能 | Rust | 零成本抽象、内存安全 |
| 企业级 | Java | 成熟稳定、易于维护 |
| 已有 .NET 项目 | C# | 无缝集成 |
| 数据分析 | Python/R | 数据处理库丰富 |

---

## 七十、可视化/无代码爬虫

对于非技术用户或快速原型开发，可视化爬虫工具可以大幅降低门槛。

### EasySpider ⭐44278
```
特点：
- 可视化浏览器自动化测试/数据采集
- 无需编码，图形化设计爬虫任务
- 支持复杂交互（点击、输入、滚动）
- 内置数据导出（CSV/Excel/JSON）
- 跨平台（Windows/Mac/Linux）

适用场景：
- 非技术用户的数据采集
- 快速验证爬取逻辑
- 教学和演示
- 小批量数据收集
```

**工作流程**：
1. 打开目标网页
2. 点击要提取的元素
3. 设置提取规则（文本/属性/链接）
4. 配置翻页/循环逻辑
5. 运行并导出数据

### 其他可视化工具
| 工具 | 特点 | 平台 |
|------|------|------|
| Portia | Scrapy 的可视化界面 | Web |
| web-scraper-chrome-extension | Chrome 扩展 | 浏览器 |
| spider-flow | 可视化蜘蛛框架 | Web |
| ParseHub | 云端可视化爬虫 | SaaS |
| Octoparse | 商业级可视化爬虫 | SaaS |

### 何时使用可视化爬虫
✅ **适用**：
- 快速原型验证
- 非技术团队成员
- 一次性数据收集
- 教学和培训

❌ **不适用**：
- 大规模生产爬虫
- 需要复杂逻辑（条件分支、API 调用）
- 高频定时任务
- 需要版本控制和协作

---

## 七十一、域名特定爬虫实战

ScrapFly 维护了 35+ 个热门网站的爬虫模板，覆盖主要行业。

### 电商类
| 网站 | 数据类型 | 关键技术 |
|------|----------|----------|
| Amazon | 产品、评论、搜索 | 反爬绕过、API 逆向 |
| eBay | 产品、变体、搜索 | 动态加载、多地区 |
| AliExpress | 产品、评论 | JS 渲染、国际化 |
| Etsy | 产品、店铺、搜索 | 手工艺品特殊结构 |
| Walmart | 产品、库存 | GraphQL API |
| BestBuy | 产品、促销 | 公开 API + 爬取 |

### 房产类
| 网站 | 地区 | 数据 |
|------|------|------|
| Zillow | 美国 | 房产列表、估价 |
| Redfin | 美国 | 房源、成交记录 |
| Realtor.com | 美国 | MLS 数据 |
| Domain.com.au | 澳洲 | 房产、拍卖 |
| Zoopla | 英国 | 房产、估价 |
| Idealista | 西班牙 | 房产、租金 |
| ImmobilienScout24 | 德国 | 房产、租赁 |

### 职业/招聘类
| 网站 | 数据 | 挑战 |
|------|------|------|
| LinkedIn | 个人资料、公司 | 强反爬、登录要求 |
| Indeed | 职位列表 | 地区差异、动态加载 |
| Glassdoor | 公司评价、薪资 | 登录墙 |
| Wellfound | 创业公司职位 | 相对宽松 |
| Crunchbase | 公司、投资人 | API 限制 |

### 社交/内容类
| 网站 | 数据 | 方案 |
|------|------|------|
| Instagram | 帖子、用户 | 移动端 API |
| Reddit | 帖子、评论 | 官方 API + 爬取 |
| YouTube | 视频、评论 | YouTube Data API |
| Bing | 搜索结果 | 相对宽松 |
| Google | 搜索结果 | 困难、考虑 SerpAPI |

### 旅游/预订类
| 网站 | 数据 | 技术 |
|------|------|------|
| Booking.com | 酒店、价格 | 动态定价、反爬 |
| Yelp | 商家、评价 | 地区限制 |
| YellowPages | 企业信息 | 相对简单 |

### 实战模式总结

#### 电商爬虫通用模式
```python
# 1. 产品页面
product_data = {
    "title": extract_title(),
    "price": extract_price(),  # 注意货币和促销
    "images": extract_images(),  # 高清图片 URL
    "description": extract_description(),
    "specifications": extract_specs(),  # 表格数据
    "reviews": extract_reviews(),  # 分页加载
    "availability": check_stock(),
}

# 2. 搜索页面
search_results = []
for page in range(1, max_pages + 1):
    items = extract_search_results(page)
    for item in items:
        product_url = item["url"]
        # 访问详情页获取完整数据
        product = scrape_product(product_url)
        search_results.append(product)
```

#### 房产爬虫通用模式
```python
property_data = {
    "address": extract_address(),
    "price": extract_price(),
    "bedrooms": extract_bedrooms(),
    "bathrooms": extract_bathrooms(),
    "area_sqft": extract_area(),
    "property_type": extract_type(),
    "listing_date": extract_date(),
    "photos": extract_photos(),
    "description": extract_description(),
    "features": extract_features(),  # 设施列表
    "location": {
        "lat": extract_latitude(),
        "lng": extract_longitude(),
    }
}
```

---

## 七十二、文档解析深度指南

### Dedoc ⭐716 - 通用文档解析系统
```
特点：
- 统一处理多种格式（DOCX/PDF/HTML/图片）
- 自动提取逻辑结构（标题、列表、表格）
- 支持扫描件 OCR（Tesseract）
- REST API 服务
- Docker 部署

支持格式：
- Office: DOCX, XLSX, PPTX
- Web: HTML, MHTML
- PDF: 文本层 PDF + 扫描件 PDF
- 图片: PNG, JPG（通过 OCR）
- 其他: TXT, CSV, JSON
```

**使用示例**：
```python
# Docker 部署
# docker run -p 1231:1231 dedocproject/dedoc

import requests

# 上传文档并解析
files = {"file": open("document.pdf", "rb")}
response = requests.post(
    "http://localhost:1231/parse",
    files=files,
    params={"format": "json"}
)

result = response.json()
# result 包含：
# - 文本内容（带结构标记）
# - 表格数据（结构化）
# - 元数据（作者、日期等）
# - 格式信息（字体、样式）
```

### PDF 解析工具对比
| 工具 | 类型 | 适用场景 |
|------|------|----------|
| pdfplumber | Python 库 | 文本 PDF、表格提取 |
| PyMuPDF (fitz) | Python 库 | 高性能、多功能 |
| Tabula | Java 工具 | 专注表格提取 |
| OCRmyPDF | 命令行 | 扫描件转可搜索 PDF |
| Dedoc | 服务 | 统一多格式处理 |

### 表格提取策略
```python
import pdfplumber

def extract_tables_from_pdf(pdf_path):
    tables = []
    with pdfplumber.open(pdf_path) as pdf:
        for page in pdf.pages:
            # 自动检测表格
            page_tables = page.extract_tables()
            for table in page_tables:
                # 清理数据
                cleaned = []
                for row in table:
                    cleaned_row = [
                        cell.strip() if cell else "" 
                        for cell in row
                    ]
                    cleaned.append(cleaned_row)
                tables.append(cleaned)
    return tables
```

### 扫描件处理流程
```
1. 图像预处理
   - 二值化
   - 去噪
   - 倾斜校正
   - 分辨率调整

2. OCR 识别
   - Tesseract（开源）
   - PaddleOCR（中文优化）
   - EasyOCR（多语言）

3. 后处理
   - 文本校正
   - 结构识别
   - 表格重建
```

---

## 七十三、User-Agent 管理策略

### crawler-user-agents ⭐1387
```
维护了所有已知爬虫/机器人的 User-Agent 模式
用途：
- 识别爬虫流量（服务器端）
- 选择合适的 UA（客户端）
- 避免被误判为恶意爬虫
```

### User-Agent 最佳实践

#### 1. 真实浏览器 UA
```python
REAL_UAS = [
    # Chrome Windows
    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36",
    # Chrome Mac
    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36",
    # Firefox Windows
    "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:133.0) Gecko/20100101 Firefox/133.0",
    # Safari Mac
    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.0 Safari/605.1.15",
]

import random
def get_random_ua():
    return random.choice(REAL_UAS)
```

#### 2. 完整请求头
```python
def get_full_headers():
    ua = get_random_ua()
    headers = {
        "User-Agent": ua,
        "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8",
        "Accept-Language": "en-US,en;q=0.9",
        "Accept-Encoding": "gzip, deflate, br",
        "Connection": "keep-alive",
        "Upgrade-Insecure-Requests": "1",
        "Sec-Fetch-Dest": "document",
        "Sec-Fetch-Mode": "navigate",
        "Sec-Fetch-Site": "none",
        "Sec-Fetch-User": "?1",
    }
    
    # 根据 UA 添加对应的 sec-ch-ua
    if "Chrome" in ua:
        headers["Sec-CH-UA"] = '"Chromium";v="131", "Not_A Brand";v="24"'
        headers["Sec-CH-UA-Mobile"] = "?0"
        headers["Sec-CH-UA-Platform"] = '"Windows"' if "Windows" in ua else '"macOS"'
    
    return headers
```

#### 3. UA 轮换策略
```python
class UARotator:
    def __init__(self, ua_list):
        self.ua_list = ua_list
        self.index = 0
    
    def get_next(self):
        """顺序轮换"""
        ua = self.ua_list[self.index]
        self.index = (self.index + 1) % len(self.ua_list)
        return ua
    
    def get_random(self):
        """随机选择"""
        return random.choice(self.ua_list)
    
    def get_for_session(self, session_id):
        """同一会话使用同一 UA"""
        index = hash(session_id) % len(self.ua_list)
        return self.ua_list[index]
```

---

## 七十四、数据质量与验证框架

### 验证层次
```
1. Schema 验证 - 数据结构是否正确
2. 类型验证 - 字段类型是否匹配
3. 范围验证 - 数值是否在合理范围
4. 完整性验证 - 必填字段是否存在
5. 一致性验证 - 跨字段逻辑是否一致
6. 唯一性验证 - 是否有重复数据
```

### Pydantic 验证示例
```python
from pydantic import BaseModel, Field, validator
from typing import Optional, List
from datetime import datetime

class Product(BaseModel):
    title: str = Field(..., min_length=1, max_length=500)
    price: float = Field(..., gt=0, lt=1000000)
    currency: str = Field(..., pattern=r"^[A-Z]{3}$")  # USD, EUR, etc.
    url: str = Field(..., pattern=r"^https?://")
    images: List[str] = Field(default_factory=list)
    rating: Optional[float] = Field(None, ge=0, le=5)
    review_count: Optional[int] = Field(None, ge=0)
    scraped_at: datetime = Field(default_factory=datetime.now)
    
    @validator("title")
    def clean_title(cls, v):
        return v.strip()
    
    @validator("price")
    def validate_price(cls, v, values):
        # 价格合理性检查
        if v > 100000:
            raise ValueError(f"Price {v} seems too high")
        return v

# 使用
try:
    product = Product(**scraped_data)
    # 验证通过，数据可用
except ValidationError as e:
    # 验证失败，记录错误
    log_error(e)
```

### 数据质量检查清单
```python
def check_data_quality(items):
    issues = []
    
    # 1. 空值检查
    for field in ["title", "price", "url"]:
        null_count = sum(1 for item in items if not item.get(field))
        if null_count > 0:
            issues.append(f"Field '{field}' has {null_count} null values")
    
    # 2. 重复检查
    urls = [item.get("url") for item in items]
    duplicates = len(urls) - len(set(urls))
    if duplicates > 0:
        issues.append(f"Found {duplicates} duplicate URLs")
    
    # 3. 异常值检查
    prices = [item.get("price", 0) for item in items if item.get("price")]
    if prices:
        avg_price = sum(prices) / len(prices)
        outliers = [p for p in prices if p > avg_price * 10 or p < avg_price * 0.1]
        if outliers:
            issues.append(f"Found {len(outliers)} price outliers")
    
    # 4. 完整性检查
    for item in items:
        required_fields = ["title", "url"]
        missing = [f for f in required_fields if not item.get(f)]
        if missing:
            issues.append(f"Item missing fields: {missing}")
    
    return issues
```

---

## 七十五、错误恢复与弹性设计

### 重试策略

#### 指数退避 + 抖动
```python
import time
import random

def exponential_backoff(attempt, base_delay=1, max_delay=60):
    """指数退避 + 随机抖动"""
    delay = min(base_delay * (2 ** attempt), max_delay)
    jitter = random.uniform(0, delay * 0.5)
    return delay + jitter

def retry_with_backoff(func, max_attempts=5):
    for attempt in range(max_attempts):
        try:
            return func()
        except Exception as e:
            if attempt == max_attempts - 1:
                raise
            delay = exponential_backoff(attempt)
            print(f"Attempt {attempt + 1} failed, retrying in {delay:.2f}s...")
            time.sleep(delay)
```

#### 装饰器模式
```python
from functools import wraps

def retry(max_attempts=3, delay=1, backoff=2, exceptions=(Exception,)):
    def decorator(func):
        @wraps(func)
        def wrapper(*args, **kwargs):
            current_delay = delay
            for attempt in range(max_attempts):
                try:
                    return func(*args, **kwargs)
                except exceptions as e:
                    if attempt == max_attempts - 1:
                        raise
                    print(f"Retry {attempt + 1}/{max_attempts} after {current_delay}s: {e}")
                    time.sleep(current_delay)
                    current_delay *= backoff
        return wrapper
    return decorator

# 使用
@retry(max_attempts=5, delay=2, backoff=2, exceptions=(ConnectionError, Timeout))
def fetch_data(url):
    response = requests.get(url, timeout=10)
    response.raise_for_status()
    return response.json()
```

### 断路器模式（Circuit Breaker）
```python
from enum import Enum
from datetime import datetime, timedelta

class CircuitState(Enum):
    CLOSED = "closed"      # 正常状态
    OPEN = "open"          # 熔断状态
    HALF_OPEN = "half_open"  # 半开状态

class CircuitBreaker:
    def __init__(self, failure_threshold=5, recovery_timeout=60):
        self.failure_threshold = failure_threshold
        self.recovery_timeout = recovery_timeout
        self.failure_count = 0
        self.last_failure_time = None
        self.state = CircuitState.CLOSED
    
    def call(self, func, *args, **kwargs):
        if self.state == CircuitState.OPEN:
            if self._should_attempt_reset():
                self.state = CircuitState.HALF_OPEN
            else:
                raise Exception("Circuit breaker is OPEN")
        
        try:
            result = func(*args, **kwargs)
            self._on_success()
            return result
        except Exception as e:
            self._on_failure()
            raise
    
    def _should_attempt_reset(self):
        return datetime.now() - self.last_failure_time > timedelta(seconds=self.recovery_timeout)
    
    def _on_success(self):
        self.failure_count = 0
        self.state = CircuitState.CLOSED
    
    def _on_failure(self):
        self.failure_count += 1
        self.last_failure_time = datetime.now()
        if self.failure_count >= self.failure_threshold:
            self.state = CircuitState.OPEN

# 使用
breaker = CircuitBreaker(failure_threshold=5, recovery_timeout=300)

def fetch_with_breaker(url):
    return breaker.call(requests.get, url, timeout=10)
```

### 降级策略
```python
def fetch_with_fallback(primary_func, fallback_func, *args, **kwargs):
    """主函数失败时使用备用函数"""
    try:
        return primary_func(*args, **kwargs)
    except Exception as e:
        print(f"Primary function failed: {e}, using fallback")
        return fallback_func(*args, **kwargs)

# 示例：主 API 失败时使用缓存
def get_product_data(product_id):
    def fetch_from_api():
        return api_client.get_product(product_id)
    
    def fetch_from_cache():
        return cache.get(f"product:{product_id}")
    
    return fetch_with_fallback(fetch_from_api, fetch_from_cache)
```

---

## 七十六、配置管理最佳实践

### 分层配置
```python
# config.py
import os
from dataclasses import dataclass
from typing import Optional

@dataclass
class ScraperConfig:
    # 基础配置
    base_url: str
    max_pages: int = 100
    delay: float = 1.0
    
    # 代理配置（从环境变量）
    proxy_url: Optional[str] = os.getenv("PROXY_URL")
    
    # API 密钥（从环境变量）
    api_key: Optional[str] = os.getenv("API_KEY")
    
    # 数据库配置
    db_url: str = os.getenv("DATABASE_URL", "sqlite:///data.db")
    
    # 日志级别
    log_level: str = os.getenv("LOG_LEVEL", "INFO")
    
    @classmethod
    def from_env(cls):
        """从环境变量加载配置"""
        return cls(
            base_url=os.getenv("BASE_URL", "https://example.com"),
            max_pages=int(os.getenv("MAX_PAGES", "100")),
            delay=float(os.getenv("DELAY", "1.0")),
        )

# 使用
config = ScraperConfig.from_env()
```

### .env 文件管理
```bash
# .env.example（提交到 Git）
BASE_URL=https://example.com
MAX_PAGES=100
DELAY=1.0
# PROXY_URL=http://proxy:8080  # 取消注释并填入实际值
# API_KEY=your_api_key_here    # 取消注释并填入实际值

# .env（不提交到 Git）
BASE_URL=https://example.com
MAX_PAGES=100
DELAY=1.0
PROXY_URL=http://user:pass@proxy.example.com:8080
API_KEY=sk_live_abc123xyz
DATABASE_URL=postgresql://user:pass@localhost:5432/scraper_db
```

### 配置验证
```python
from pydantic import BaseSettings, validator

class Settings(BaseSettings):
    base_url: str
    api_key: str
    max_concurrent: int = 10
    
    @validator("base_url")
    def validate_url(cls, v):
        if not v.startswith("http"):
            raise ValueError("base_url must start with http/https")
        return v
    
    @validator("api_key")
    def validate_api_key(cls, v):
        if len(v) < 10:
            raise ValueError("API key seems too short")
        return v
    
    class Config:
        env_file = ".env"
        env_file_encoding = "utf-8"

# 自动从 .env 加载并验证
settings = Settings()
```

### 多环境配置
```python
# config/
# ├── base.py      # 基础配置
# ├── dev.py       # 开发环境
# ├── staging.py   # 测试环境
# └── prod.py      # 生产环境

# base.py
class BaseConfig:
    MAX_PAGES = 100
    DELAY = 1.0
    LOG_LEVEL = "INFO"

# dev.py
from .base import BaseConfig

class DevConfig(BaseConfig):
    MAX_PAGES = 10  # 开发时减少页数
    DELAY = 0.5     # 开发时减少延迟
    LOG_LEVEL = "DEBUG"

# prod.py
class ProdConfig(BaseConfig):
    MAX_PAGES = 10000
    DELAY = 2.0
    LOG_LEVEL = "WARNING"

# 根据环境变量选择
import os
env = os.getenv("ENV", "dev")
config = {
    "dev": DevConfig,
    "staging": StagingConfig,
    "prod": ProdConfig,
}[env]()
```

---

## 七十七、源码学习：从 GitHub 高星项目学到的架构设计（29项目完整版）

### 隐身层：4 层防御体系

#### Project 1: puppeteer-extra-plugin-stealth（⭐7,381）— 19 项 JS 检测修复
19个独立evasion模块：navigator.webdriver、chrome.runtime、iframe.contentWindow、webgl.vendor、navigator.plugins、media.codecs等。每个检测点一个插件，可插拔。
```bash
npm install puppeteer-extra puppeteer-extra-plugin-stealth
```

#### Project 2: rebrowser-patches（⭐1,400）— CDP 协议 3 泄漏修补
Runtime.Enable泄漏 + sourceURL标记 + Utility World名称。一行命令修补：
```bash
npx rebrowser-patches@latest patch --packageName playwright-core
# 或直接替换：npm install rebrowser-playwright
```

#### Project 3: undetected-chromedriver（⭐12,763）— Driver 二进制 patcher
```python
import undetected_chromedriver as uc
driver = uc.Chrome()  # 自动下载、patch、隐身
```

#### Project 4: curl_cffi（⭐6,125）— TLS/JA3 指纹伪装（37种浏览器）
```python
from curl_cffi import requests
r = requests.get(url, impersonate='chrome120')  # 5MB内存 vs Playwright 500MB
```

#### Project 5: Fortress（⭐395）— 34 个 C++ 引擎级指纹补丁
```python
with Fortress() as f:
    browser = playwright.chromium.connect_over_cdp(f.cdp_url)
# CreepJS全绿，Sannysoft全绿
```

#### Project 6: nodriver（⭐4,546）— CDP 直连，零依赖
```python
import nodriver as uc
browser = await uc.start()  # 不要 driver，不要 Selenium
tab = await browser.get(url)
await tab.cf_verify()  # 内置 Cloudflare 验证
```

#### Project 7: HasData/cloudflare-bypass（⭐39）— 6 级渐进式绕过
基础→Stealth→代理→UA随机→人类行为→全流程。人类行为：贝塞尔鼠标+分块滚动+随机点击。


### 框架层：5 种语言/架构

#### Project 8: scrapy（⭐63,295）— Python 企业级框架
三大模式：(1)20+信号事件驱动 (2)重试降优先级(priority+=负值) (3)媒体管道去重(downloading集合)

#### Project 9: scrapy-redis（⭐5,644）— 分布式爬虫
3个组件替换为Redis版(Scheduler+DupeFilter+Item Pipeline)，单机变集群。

#### Project 10: pyspider（⭐16,796）— 分布式参考架构
Scheduler→MessageQueue→Fetcher→Processor→ResultWorker，进程解耦。

#### Project 11: ruia（⭐1,740）— Python 异步微框架
三大模式：(1)deque双向中间件链 (2)ItemMeta元类声明式字段 (3)SpiderHook生命周期钩子

#### Project 12: crawlee（⭐24,863）— Node.js 浏览器池
3大自动管理：单浏览器20页上限、100页后自动退休、5分钟空闲关闭。每会话唯一指纹。

#### Project 13: colly（⭐25,383）— Go 并发爬虫
内置robots.txt遵守、HTTP缓存、域名限速(Delay+Parallelism)、OnHTML装饰器。

#### Project 14: splash（⭐4,190）— 浏览器即无状态 API
Lua脚本→QT5渲染→JSON返回。Scrapy通过scrapy-splash中间件调用，无状态水平扩展。


### 提取层：正文+摘要+PDF

#### Project 15: readability（⭐11,352）— 内容分算法鼻祖
`score = commaCount + textLen/100 - linkDensity*0.5 + classWeight`，最高分祖先=正文容器。

#### Project 16: newspaper3k（⭐15,120）— 新闻四维摘要
`totalScore = (titleFeature×1.5 + frequency×2.0 + sentenceLength + position) / 4.0`

#### Project 17: trafilatura（⭐6,326）— 管线式提取
CLI+Python双模式，自动剔导航广告，输出CSV/JSON/XML/TXT。

#### Project 18: camelot（⭐3,789）+ tabula（⭐7,446）— PDF 表格
```python
tables = camelot.read_pdf('report.pdf')  # → pandas DataFrame
```


### 引擎层：自适应+AI+即服务

#### Project 19: Scrapling（⭐70,779）— 自适应选择器
`auto_save=True`保存元素多维指纹，`adaptive=True`改版后自动relocate。

#### Project 20: Scrapegraph-ai（⭐28,570）— AI自然语言爬取
```python
graph = SmartScraperGraph(prompt="提取产品名和价格", llm_config={"model":"gpt-4"})
```

#### Project 21: firecrawl（⭐154,575）— 爬虫即服务
6种语言SDK + 微服务架构(API/Playwright/Redis/Postgres)，可水平扩展。

#### Project 22: autoscraper（⭐7,667）— 示例驱动学习
只给一个示例值，自动学习提取规则，无需写选择器。


### 社交层：无 API 多平台

#### Project 23: twint（⭐16,393）— Twitter 无 API
无需认证、无频率限制、可拿全部推文。

#### Project 24: snscrape（⭐5,426）— 多平台统一
Facebook/Instagram/Twitter/Reddit/Telegram/VK/Weibo 7平台统一接口。


### OSINT 层：侦查+邮箱+域名

#### Project 25: Photon（⭐13,059）— OSINT 极速爬虫
提取URLs、邮箱、社交账号、文件、密钥/Token。3层深度+100线程。

#### Project 26: theHarvester（⭐16,862）— 邮箱+域名侦查
20+公共数据源收集：Google/Bing/Shodan/证书透明度。

#### Project 27: spiderfoot（⭐19,818）— OSINT 自动化
全自动威胁情报收集和攻击面映射。

#### Project 28: maigret（⭐35,680）— 用户名全网侦查
3000+网站自动搜索用户名是否注册。


### 设施层：代理+OCR+管理

#### Project 29: proxy_pool（⭐23,518）— 免费代理池
采集→验证→API三阶段，RESTful接口获取可用代理。

### 综合能力矩阵

| 层 | 项目 | ⭐ |
|---|------|-----|
| 隐身 | nodriver+puppeteer-extra+curl_cffi+undetected+rebrowser+Fortress | 32K |
| 框架 | scrapy+scrapy-redis+pyspider+ruia+crawlee+colly+splash | 140K |
| 提取 | readability+newspaper3k+trafilatura+camelot+tabula | 44K |
| 引擎 | Scrapling+Scrapegraph+firecrawl+autoscraper | 261K |
| 社交 | twint+snscrape | 22K |
| OSINT | Photon+theHarvester+spiderfoot+maigret | 85K |
| 设施 | proxy_pool | 24K |

**总计：29 项目 | ⭐608K+ | 完整爬虫生态覆盖**

### 已内化的核心设计模式（从源码提取）

以下 9 个模式已存入长期记忆，可直接调用于实战：

**1. scrapy 信号系统** — 爬虫生命周期事件驱动
```
engine_started → spider_opened → request_scheduled
→ response_received → item_scraped → spider_closed → engine_stopped
```
替代大 try/except，每个信号独立处理。

**2. ruia 中间件链** — 请求/响应双 deque
请求前按顺序过、响应后逆序过，可插拔组合。
```python
middleware.request_middleware.append(RetryMiddleware)
middleware.response_middleware.appendleft(ThrottleMiddleware)
```

**3. crawlee 浏览器池** — 自动退休+空闲回收
单浏览器最多 N 页、处理 M 页后退休换新、空闲 T 秒关闭。

**4. ruia 元类 Item** — 声明式字段
ItemMeta 自动收集 BaseField，字段定义+自动清洗分离。

**5. 管道去重** — downloading 集合
相同 URL 只下载一次，重复请求等缓存。

**6. 重试降优先级** — priority+=负值
重试请求不插队，让新请求优先生效。

**7. Scrapling 自适应模式**
```python
auto_save=True → 保存元素特征指纹
adaptive=True  → 改版后自动 relocate
```

**8. readability 内容分算法**
score = commaCount + textLen/100 - linkDensity×0.5 + classWeight

**9. newspaper3k 四维摘要**
totalScore = (titleFeature×1.5 + frequency×2.0 + sentenceLength + position) / 4.0

### Playwright 爬虫最佳实践（从实战+源码固化）

1. 等页面用 `domcontentloaded` 不用 `networkidle`（中国网站外部资源多）
2. API 接口先获取 session cookie 再发请求，不要直接 POST
3. 调用页面已有的 `$.ajax()` 比自己发 HTTP 更可靠（自动继承 cookie/csrf/session）
4. 数据字段用 `.get()` 加默认值，不用 `item['key']` 直接访问
5. 大文件编辑不用 `edit_file`，用 `shell` + Python 直接写
6. 浏览器路径：先试子域名（zs→zsw），再试无头浏览器，最后 API 逆向

## 七十八、工具速查表（完整版）

### 请求与渲染
| 工具 | 安装 | 一句话用途 |
|------|------|------------|
| curl + grep | 系统自带 | 快速抓单页、定位关键词 |
| requests + BS4 | pip install | 静态页批量抓取 |
| Playwright | pip install + playwright install chromium | JS 渲染 / 基础反爬 |
| **Patchright** | pip install patchright | Playwright 无缝替换，去自动化标记 |
| **CloakBrowser** | pip install cloakbrowser | 源码级隐身 Chromium，过 Cloudflare/Turnstile |
| **Camoufox** | pip install camoufox | Firefox 源码级反检测，AI Agent 多会话 |
| **Botright** | pip install botright | Playwright + 指纹 + AI 验证码解决 |
| **Scrapling** | pip install scrapling | 自适应选择器 + 隐身爬取 + Spider 框架 |

### 框架与平台
| 工具 | 安装 | 一句话用途 |
|------|------|------------|
| **Trawl** | docker compose up | 自托管 WAF 绕过引擎（4 层递进） |
| crawl4ai-skill | pip install crawl4ai-skill | 搜索+爬取一体化 CLI |
| **Crawl4AI** | pip install crawl4ai | 开源 LLM 友好爬虫，Docker+MCP |
| crawlee | pip install 'crawlee[all]' | 生产级框架（指纹/会话池/代理/自动并发） |
| Scrapy | pip install scrapy | 大规模结构化爬取 |
| scrapy-redis | pip install scrapy-redis | 分布式 Scrapy |
| **Crawlab** | docker | 分布式爬虫管理平台（任何语言） |

### 反检测与指纹
| 工具 | 安装 | 一句话用途 |
|------|------|------------|
| browserforge | pip install browserforge | 生成真实浏览器指纹+头 |
| **tls-client** | pip install tls-client | TLS 指纹伪装（模拟 Chrome/Firefox 握手） |
| **curl-impersonate** | 编译安装 | curl 模拟浏览器 TLS 指纹 |
| undetected-chromedriver | pip install | Selenium 去自动化标记 |
| **undetectable-fingerprint-browser** | GitHub 安装 | 开源 Multilogin 替代（免费） |
| **CDP-Patches** | GitHub 安装 | OS 级别 CDP 协议补丁 |
| **Fortress** | GitHub 安装 | 隐身 Chromium 引擎 |
| **Mochi** | pip install mochi | 高保真浏览器指纹库 |

### 高级代理
| 工具 | 安装 | 一句话用途 |
|------|------|------------|
| **requests-ip-rotator** ⭐1669 | pip install requests-ip-rotator | AWS API Gateway 无限 IP 池 |
| **lambda-scraper** | AWS Lambda | 无状态代理池（每次全新环境） |

### AI 与提取
| 工具 | 安装 | 一句话用途 |
|------|------|------------|
| **Jina Reader** | 无需安装 | `r.jina.ai/URL` 直接返回 Markdown |
| **Firecrawl** | pip install firecrawl-py | AI 爬取 API（需 key） |
| **browser-use** | pip install browser-use | AI Agent 自然语言驱动浏览器 |
| trafilatura | pip install trafilatura | 学术级正文提取 |
| newspaper3k | pip install newspaper3k | 新闻文章专用提取 |

### 社交媒体爬虫
| 工具 | ⭐ | 一句话用途 |
|------|-----|------------|
| **Scweet** | 1569 | Twitter/X 爬虫，无需 API Key，智能多账号 |
| **GramAddict** | 1590 | Instagram 自动化机器人（UIAutomator2） |
| **linkedin-profile-scraper-api** | 769 | LinkedIn 结构化数据提取 |
| **youtube-comment-suite** | 315 | YouTube 评论批量下载（无需 API） |
| **google-maps-scraper** | 5217 | Google Maps 数据（Go 实现，高性能） |

### 垂直领域爬虫
| 工具 | ⭐ | 一句话用途 |
|------|-----|------------|
| **amazon-scraper** | 437 | Amazon 产品数据提取 |
| **HomeHarvest** | 715 | 房地产数据（多网站聚合） |

### Go 语言方案
| 工具 | ⭐ | 一句话用途 |
|------|-----|------------|
| **HTTPCloak** | 1173 | 完美 TLS 指纹伪装（JA3/JA4/Akamai） |

### 辅助工具
| 工具 | 安装 | 一句话用途 |
|------|------|------------|
| **mitmproxy** | pip install mitmproxy | API 逆向抓包（TLS 中间人） |
| **changedetection.io** | docker run | 网页变化监控 + 通知 |
| chardet | pip install chardet | 编码自动检测 |
| feedparser | pip install feedparser | RSS/Atom 解析 |
| PaddleOCR | pip install paddleocr | 中文图片文字识别 |
| pymupdf | pip install pymupdf | PDF 内容提取 |
| pybloom-live | pip install pybloom-live | Bloom Filter 去重 |
| 2captcha-python | pip install 2captcha-python | 验证码解决 API |
| **browsers-benchmark** | GitHub 安装 | 浏览器自动化工具基准测试 |

### 反爬 API 服务（付费）
| 服务 | 用途 |
|------|------|
| Scrapfly | 全托管爬取（JS渲染+反爬+截图） |
| ZenRows | 专注反爬绕过 |
| HyperSolutions | Akamai/DataDome/Kasada token 生成 |
| Crawlbase | 99% 成功率智能代理 |
| 2captcha / Anti-Captcha | 验证码人工/AI 解决 |

---

---

## 七、实战复盘记录

### 复盘流程（每次爬取后必做）

```
爬取完成 → 回顾全程 → 找出好/坏实践 → 更新本指南
```

### 复盘案例：南华大学保研名单爬取（2026-07-23）

#### 背景
用户要求爬取"南华大学2025年保研名单"。实际爬取到的是南华大学2026年推免拟录取名单（2025年10月28日发布，31人）。

#### 执行流程
```
用户请求 → Playwright搜百度(被拦截) → Bing超时 → DuckDuckGo无效 
→ curl直连学校官网 → 分析源码找到yjs子站 → 发现推免PDF → 下载解析完成
```

#### ✅ 做得好
1. **先测连通性**：用 `curl -w "%{http_code} %{size_download}"` 快速确认主站可访问，是找到突破口的关键
2. **多搜索渠道切换**：百度→Bing→DuckDuckGo→官网直连，穷举可行方案
3. **源码分析细致**：找到主站到yjs子站的导航链，发现关键推免页面
4. **PDF下载纠错**：首次返回HTML后，加上 `Referer` header 重试成功
5. **文件分类存放**：`/workspace/scrapers/` 和 `/workspace/data/` 分开管理

#### ❌ 待改进
| 问题 | 改进方案 |
|------|----------|
| **Playwright白写脚本**：先花时间写Playwright搜百度，被拦截后脚本作废 | **先用curl/shell试探**，确认目标能爬再写完整脚本，避免前期投入浪费 |
| **目录未预先创建**：`mkdir -p` 忘记执行，首次文件写入失败 | 涉及文件操作前，先 `mkdir -p` 确保路径存在 |
| **网络不通的诊断效率低**：逐个写脚本测试Bing/DuckDuckGo，耗时过长 | 先 `for url in ...; do curl -o /dev/null -w "%{http_code} %{time_total}s" -s "$url"; done` 横向测试多个目标 |
| **PDF表格解析效果差**：pdfminer.six 解析表格格式混乱 | 表格类PDF优先用 `tabula-py` 或 `camelot` 专门提取表格 |
| **用户意图理解延迟**：用户说"2025年"，实际是"2026届推免"（年份命名规则颠倒），未第一时间解释 | 遇到命名歧义时，及时向用户澄清"你指的是**入学年份**还是**发布年份**？" |

#### 🔧 优化后的高校/政府类信息爬取流程

```
需求："XX大学20XX年XXX名单"

Step 1: 横向试探（curl -w）
  curl -o /dev/null -w "%{http_code} %{time_total}s" -sL \
    "https://www.xxx.edu.cn" \
    "https://yjs.xxx.edu.cn" \
    "https://jwc.xxx.edu.cn"

Step 2: 官网源码分析 → 找导航链（通知公告 / 招生信息 / 公示公告）
  curl -sL "https://yjs.xxx.edu.cn" | grep -oP 'href="[^"]*"' | head

Step 3: 搜索关键词（curl+grep 搜百度非JS版，免Playwright）
  curl -sL "https://www.baidu.com/s?wd=site:xxx.edu.cn+推免+名单" | \
    grep -oP '<h3[^>]*>.*?</h3>' | sed 's/<[^>]*>//g'

Step 4: 下载附件
  - 先爬详情页，找到附件链接（.pdf/.xls/.doc）
  - curl下载时加上 Referer header（很多CMS系统需要校验）
  - 检查 Content-Type 确认是文件而非HTML提示页

Step 5: 解析
  - PDF表格 → tabula-py / camelot
  - PDF文字 → pdfminer.six / pymupdf
  - Word文档 → python-docx
  - 网页表格 → pd.read_html()
```

#### 关于多线程的建议

| 阶段 | 推荐 | 不推荐 |
|------|------|--------|
| **试探期**（测多个URL连通性） | ✅ **多线程**。`for`循环逐个curl太慢，应同时curl多个URL | ❌ 逐个等待耗时 |
| **搜索期**（同一目标多引擎搜索） | ✅ **多线程**。同时搜百度+Bing+DuckDuckGo 多个搜索引擎 | ❌ 串行导致大量等待 |
| **爬列表页**（翻多页） | ✅ **多线程**。ThreadPoolExecutor 分页抓取 | ❌ 逐页爬 |
| **下载附件**（PDF/Word） | ❌ **不建议**。学校服务器带宽有限，同时下载多个大文件可能触发限流 | ✅ 串行加延迟下载 |
| **高频请求**（同域名>10次/分钟） | ❌ **不建议**。容易被封IP | ✅ 串行+随机延迟 |

**多线程三原则**：
1. **试探期用多线程** — `curl -o /dev/null` 测试连通性可并发，不影响服务器
2. **下载期用单线程+限速** — 避免触发反爬
3. **同一域名控制并发数 ≤ 5** — 用 `ThreadPoolExecutor(max_workers=5)` 控制

```bash
# 示例：多线程试探URL连通性
urls=(
  "https://www.xxx.edu.cn"
  "https://yjs.xxx.edu.cn"
  "https://jwc.xxx.edu.cn"
  "https://www.baidu.com/s?wd=xxx"
)
for url in "${urls[@]}"; do
  (curl -o /dev/null -w "%{http_code} %{time_total}s" -sL "$url" -H "User-Agent: Mozilla/5.0" &)
done
wait
```

#### 核心经验总结
> **不要从Playwright开始，要从curl开始。**
> 80%的学校官网使用静态HTML/CMS系统，curl+BS4足以应对。Playwright应作为最后手段，仅当curl拿不到内容时才用。搜索也如此：百度非JS版（curl）先试，不行再考虑Playwright。

---

---

## 八、反爬实战：挫折与对策全记录

### 实战背景：爬取南华大学推免推荐名单（2026-07-23）

本记录基于一次搜索南华大学2024-2025年推免推荐名单的真实爬取经历，几乎踩遍了所有典型反爬坑。

---

### 挫折清单 & 对策（按出现顺序）

#### 挫折1：Playwright写脚本 → Baidu captcha拦截
**现象**：花时间写了Playwright搜百度，结果百度返回安全验证页面，脚本白写。

**原因**：云服务器IP在百度黑名单中，再加上headless Chrome特征明显。

**对策**：
```
❌ 不要一上来就写Playwright
✅ 先 curl -w "%{http_code}" 测试连通性
✅ 能curl拿到的就不要用Playwright
✅ Playwright留作最后手段（JS渲染/Cloudflare）
```

**GitHub高星项目**：
- AtuboDad/playwright_stealth（⭐978）— Playwright隐身插件，利用puppeteer-extra-plugin-stealth的JS注入技术绕过检测
- CloakHQ/CloakBrowser（⭐28950）— C++源码级别修改Chromium指纹，71个补丁，通过所有反爬检测

---

#### 挫折2：逐个测试搜索引擎耗时过长
**现象**：串行测试Baidu→Bing→DuckDuckGo→Google，每个等超时才切下一个。

**原因**：没有并发试探。

**对策**：
```bash
# ✅ 多线程同时测多个搜索引擎/目标
for url in "baidu_url" "bing_url" "target_site"; do
  (curl -o /dev/null -w "%{http_code}|%{time_total}s" -sL "$url" &)
done
wait
```

---

#### 挫折3：百度搜索返回227B/1488B空页面（反爬加强）
**现象**：第一次搜"南华大学2025年保研名单"成功了，后续所有搜索都返回227B或1488B（验证码页面）。

**原因**：
- 短时间内同一IP多次搜索，触发频率限制
- 缺少完整浏览器指纹头（`Sec-Fetch-*`, `Sec-Ch-Ua`, `Accept-Encoding: br` 等）

**对策**：
```bash
# ✅ 必须添加完整的浏览器请求头
curl -s -L "https://www.baidu.com/s?wd=关键词" \
  -H "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/125.0.0.0 Safari/537.36" \
  -H "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7" \
  -H "Accept-Language: zh-CN,zh;q=0.9,en;q=0.8" \
  -H "Accept-Encoding: gzip, deflate, br" \
  -H "Sec-Ch-Ua: \"Google Chrome\";v=\"125\", \"Chromium\";v=\"125\", \"Not.A/Brand\";v=\"24\"" \
  -H "Sec-Ch-Ua-Mobile: ?0" \
  -H "Sec-Ch-Ua-Platform: \"Windows\"" \
  -H "Sec-Fetch-Dest: document" \
  -H "Sec-Fetch-Mode: navigate" \
  -H "Sec-Fetch-Site: none" \
  -H "Sec-Fetch-User: ?1" \
  -H "Upgrade-Insecure-Requests: 1" \
  -H "Connection: keep-alive" \
  --compressed \
  --max-time 15
```

**关键技巧**：
- `--compressed` 标志必须加（接受gzip/deflate/br压缩）
- 所有 `Sec-*` 头必须完整（反爬检测这些头是否齐全）
- `Accept-Encoding: br`（Brotli压缩）是真实Chrome的标志性特征

**结果**：加上后立即从227B → 160KB+，成功绕过。

---

#### 挫折4：Bing/Google超时（400/0B）
**现象**：Bing返回HTTP 400，Google返回HTTP 400 Bad Request。

**原因**：云IP被这些搜索引擎直接拒绝连接。

**对策**：放弃Bing/Google，集中精力用百度（加完整headers）和直连目标网站。

---

#### 挫折5：DuckDuckGo搜索返回首页而非结果页
**现象**：DuckDuckGo的HTML版搜索（`html.duckduckgo.com/html/`）返回了首页而不是搜索结果。

**原因**：DuckDuckGo对非浏览器请求也有限制。

**对策**：DuckDuckGo作为备选，不依赖它。

---

#### 挫折6：目录未预先创建导致文件写入失败
**现象**：`/workspace/scrapers/` 目录不存在，`cat << 'SCRIPT' > /workspace/scrapers/file.py` 失败。

**原因**：未执行 `mkdir -p`。

**对策**：
```bash
# ✅ 先确保目录存在
mkdir -p /workspace/scrapers /workspace/data
```

---

#### 挫折7：PDF表格解析混乱
**现象**：`pdfminer.six` 提取表格PDF时数据错位（行和列对不齐）。

**原因**：pdfminer按文字位置提取，表格格式导致坐标混乱。

**对策**：
| 场景 | 工具 |
|:----|:----|
| 文字型PDF | pdfminer.six / pymupdf |
| 表格型PDF | tabula-py / camelot |
| 扫描型PDF | PaddleOCR / tesserocr |
| .doc文件 | olefile（旧格式）/ python-docx（.docx） |

---

#### 挫折8：.doc文件无法直接读取
**现象**：`strings`命令只看到乱码，`python-docx`只能读.docx不能读.doc。

**原因**：旧版.doc是OLE2复合文档二进制格式，不是纯文本。

**对策**：
```python
import olefile
ole = olefile.OleFileIO("file.doc")
data = ole.openstream('WordDocument').read()
text = data.decode('utf-16-le', errors='ignore')
# 再用正则清理控制字符
```

---

#### 挫折9：目标数据不在中央网站
**现象**：研究生院网站只有拟录取名单，没有推荐名单。教务处、各学院网站也找不到。

**原因**：学校从2022年起改变了公示方式，推荐名单可能改为了各学院内部公示，不再统一公开发布。

**对策**：
```
✅ 通过百度搜索具体学院+年份+"拟推荐免试"找到分散的学院级页面
✅ 关键搜索词格式："南华大学 + [学院名] + [年份] + 拟推荐免试"
✅ 学院的推荐名单常以PDF/DOC附件形式发布，需要下载附件
```

---

#### 挫折10：PDF/文件附件下载失败
**现象**：`curl`下载PDF返回HTML提示页（CMS权限校验）。

**原因**：CMS系统（动易SiteFactory等）的下载链接需要校验 `Referer` 头。

**对策**：
```bash
# ✅ 下载附件时必须加 Referer
curl -s -L "附件URL" \
  -H "User-Agent: Mozilla/5.0" \
  -H "Referer: https://来源页面.htm" \
  -o output.pdf
```

---

### 反爬对抗层级总结

| 级别 | 百度/搜索引擎 | 高校官网（CMS） |
|:----:|:------------:|:--------------:|
| **L0** | UA检查 | ❌ 基本不反爬 |
| **L1** | UA + Cookie | ✅ 需要Referer下载附件 |
| **L2** | 完整浏览器头（Sec-*, Accept-Encoding） | ✅ 无其他反爬 |
| **L3** | 频率限制（IP黑名单） | ❌ 不发生 |
| **L4** | Cloudflare Turnstile/JS挑战 | ❌ 不发生 |

> 高校官网（动易/SiteServer等CMS）通常**没有任何反爬**，可直接curl抓取。**真正的反爬瓶颈在搜索引擎**——想搜到高校的内容必须先过百度这一关。

---

### GitHub高星项目推荐

| 项目 | ⭐ | 解决什么问题 |
|:----|:--:|:-----------|
| **lexiforest/curl_cffi** | 6127 | 伪装TLS/JA3指纹，用Python冒充浏览器HTTPS握手 |
| **CloakHQ/CloakBrowser** | 28950 | C++源码级修改Chromium，通过所有反爬检测（Cloudflare/FingerprintJS等） |
| **AtuboDad/playwright_stealth** | 978 | Playwright的JS注入隐身插件 |
| **berstend/puppeteer-extra** | 6500+ | Puppeteer隐身插件合集（playwright_stealth的源头） |
| **yescaptcha** | — | 付费API，绕过Cloudflare获取cf_clearance |

---

### 本次实战流程图（优化后）

```
请求 → 
  ├─ 试探（并发curl+完整headers）
  │   ├─ 目标网站直连 ← 成功！→ 直接爬取
  │   ├─ 百度搜索 ← 成功！→ 解析结果→ 找到目标
  │   └─ 其他搜索 ← 失败 → 放弃
  ├─ 解析目标页
  │   ├─ 含附件 → 加Referer下载
  │   │   ├─ .pdf → tabula/camelot/pdfminer
  │   │   └─ .doc → olefile解码
  │   └─ 正文表格 → BeautifulSoup解析
  └─ 复盘 & 更新指南 ← 必做！
```

---

### 搜索引擎备用方案

当百度IP被拉黑后，可切换以下替代搜索引擎：

| 搜索引擎 | 域名 | 反爬强度 | 实测效果 |
|:---------|:-----|:--------:|:--------:|
| **百度** | baidu.com | ⭐⭐⭐⭐⭐ | 加满headers可通过，IP封后换啥都没用 |
| **神马搜索(SM)** | sm.cn | ⭐⭐ | ✅ 阿里系，反爬弱，此环境可直接用 |
| **夸克** | quark.cn / search.quark.cn | ⭐⭐⭐ | ❌ 此环境所有入口均返回400/000，夸克对云IP限制严格，需在真实手机上使用 |
| **搜狗** | sogou.com | ⭐⭐⭐ | 未实测 |
| **Bing** | bing.com | ⭐⭐⭐⭐ | 云IP常被返回400 |
| **Google** | google.com | ⭐⭐⭐⭐⭐ | 云IP无法访问 |

**关键发现**：当百度IP被封后（返回227B），**SM搜索（sm.cn）仍然可用**，且无需加任何特殊headers，直接用curl即可。这是突破搜索限制的最佳替补方案。

```bash
# SM搜索用法（无需伪装）
curl -sL "https://www.sm.cn/s?q=南华大学+语言文学学院+唐娟+保研" \
  -H "User-Agent: Mozilla/5.0 (Linux; Android 10; K) AppleWebKit/537.36" \
  --max-time 15

# 夸克搜索
curl -sL "https://quark.sm.cn/s?q=关键词" \
  -H "User-Agent: Mozilla/5.0" --max-time 15
```

**搜索引擎选择策略**：
```
百度可用 → 优先百度（加满17个浏览器头）
百度被封 → 切SM/神马（几乎无反爬）
SM也不行 → 直连目标网站
全被封 → 等IP解封（通常30-60分钟）或用代理
```

### 核心教训（一句话总结）

> **搜索靠百度（加满headers），百度被封切SM，爬取靠curl直连（高校CMS无反爬），解析看格式选工具，复盘必写进指南。**

---

*指南版本：v7.1 | 最后更新：2026-07-23 | 82 节 | 6950+ 行 | 30 个 GitHub 高星项目源码分析（合计 ⭐638K+）*
*覆盖：隐身4层防御 + 7框架(5语言) + 4提取引擎 + 4引擎项目 + 2社交 + 4 OSINT + 代理池*
*整合来源：puppeteer-extra、rebrowser、undetected-chromedriver、curl_cffi、Fortress、nodriver、scrapy、scrapy-redis、pyspider、ruia、crawlee、colly、splash、readability、newspaper3k、trafilatura、camelot、Scrapling、Scrapegraph-ai、firecrawl、autoscraper、twint、snscrape、Photon、theHarvester、spiderfoot、maigret、proxy_pool + 实战经验*

---

# 💻 代码模板库（优化版 v2.0）

> 以下为 13 个实战脚本的优化版本。
> 优化重点：✅ 错误处理 ✅ 反检测增强 ✅ 统一模式 ✅ 类型注解 ✅ 可复用性

---

## 📦 通用工具函数

在运行以下模板前，建议先定义这些通用工具：

```python
"""🛠️ 爬虫通用工具函数"""
import time, random, re
from typing import Optional

# ── UA 轮换池 ──
USER_AGENTS = [
    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36",
    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36",
    "Mozilla/5.0 (iPhone; CPU iPhone OS 17_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.0 Mobile/15E148 Safari/604.1",
    "Mozilla/5.0 (Linux; Android 13; Pixel 7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Mobile Safari/537.36",
]

def random_ua(mobile: bool = False) -> str:
    return USER_AGENTS[2] if mobile else USER_AGENTS[0]

def random_delay(min_s: float = 1.0, max_s: float = 3.0) -> None:
    time.sleep(random.uniform(min_s, max_s))

def extract_urls(html: str) -> list[str]:
    """从HTML中提取所有http链接"""
    return re.findall(r'<a[^>]*href="(https?://[^"]+)"', html)

def safe_get_text(html: str, tag: str = "body", max_len: int = 500) -> str:
    """提取纯文本（去除HTML标签）"""
    text = re.sub(r'<script[^>]*>.*?</script>', '', html, flags=re.DOTALL)
    text = re.sub(r'<style[^>]*>.*?</style>', '', text, flags=re.DOTALL)
    text = re.sub(r'<[^>]+>', '\n', text)
    text = re.sub(r'\n{3,}', '\n\n', text).strip()
    return text[:max_len]

# ── 17个标准浏览器头 ──
def browser_headers(referer: str = "https://www.google.com/") -> list[str]:
    return [
        "-H", f"User-Agent: {random_ua()}",
        "-H", "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
        "-H", "Accept-Language: zh-CN,zh;q=0.9,en;q=0.8",
        "-H", "Accept-Encoding: gzip, deflate, br",
        "-H", "Connection: keep-alive",
        "-H", "Upgrade-Insecure-Requests: 1",
        "-H", "Sec-Fetch-Dest: document",
        "-H", "Sec-Fetch-Mode: navigate",
        "-H", "Sec-Fetch-Site: none",
        "-H", "Sec-Fetch-User: ?1",
        "-H", f"Referer: {referer}",
        "-H", "Cache-Control: max-age=0",
        "--compressed",
    ]
```

---

## search_baidu_mobile.py
```python
"""
📱 百度移动端搜索 v2
优化: 错误处理 / UA轮换 / 更精准的URL提取 / 支持翻页
"""
import subprocess, re
from typing import Optional

HEADERS = [
    "-H", "User-Agent: Mozilla/5.0 (Linux; Android 10; K) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Mobile Safari/537.36",
    "-H", "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
    "-H", "Accept-Language: zh-CN,zh;q=0.9",
    "--compressed",
]

def search_baidu(query: str, page: int = 1) -> list[dict]:
    """百度搜索，自动检测验证码拦截"""
    pn = (page - 1) * 10
    url = f"https://www.baidu.com/s?wd={query}&pn={pn}&rn=10"
    try:
        result = subprocess.run(
            ["curl", "-s", "-L", url] + HEADERS,
            capture_output=True, text=True, timeout=15
        )
        if "captcha" in result.stdout or "验证" in result.stdout:
            return [{"error": "触发验证码，建议用SM神马搜索"}]
        
        items = []
        for block in re.findall(r'<div[^>]*class="[^"]*result[^"]*"[^>]*>.*?</div>', result.stdout, re.DOTALL):
            m = re.search(r'<a[^>]*href="(https?://[^"]+)"[^>]*>(.*?)</a>', block, re.DOTALL)
            if m:
                items.append({"url": m.group(1), "title": re.sub(r'<[^>]+>', '', m.group(2)).strip()})
        return items[:10]
    except subprocess.TimeoutExpired:
        return [{"error": "超时"}]

def search_sm(query: str) -> list[dict]:
    """神马搜索（百度被封时的替补）"""
    import urllib.parse
    try:
        r = subprocess.run(["curl", "-s", "-L", f"https://m.sm.cn/s?q={query}",
                          "-H", "User-Agent: Mozilla/5.0"], capture_output=True, text=True, timeout=15)
        items = []
        for m in re.finditer(r'<a[^>]*href="[^"]*url=([^&]+)[^"]*"[^>]*>(.*?)</a>', r.stdout):
            items.append({"title": re.sub(r'<[^>]+>', '', m.group(2)).strip(),
                         "url": urllib.parse.unquote(m.group(1))})
        return items[:10]
    except: return [{"error": "SM搜索失败"}]

if __name__ == "__main__":
    import sys
    query = sys.argv[1] if len(sys.argv) > 1 else "南华大学转专业"
    for r in search_baidu(query):
        print(f"  {r.get('title','?')[:50]} → {r.get('url','?')[:80]}")
```

## search_bing_mobile.py
```python
"""
🔍 Bing搜索 v2 — Patchright/Playwright 自动降级
优化: 自动检测可用引擎 / 更稳的等待策略 / 结果过滤
"""

def search_bing(query: str, max_results: int = 10) -> list[dict]:
    """Bing搜索，自动选择 Patchright → Playwright"""
    for engine_name in ["patchright", "playwright"]:
        try:
            engine = __import__(f"{engine_name}.sync_api", fromlist=["sync_playwright"])
            with engine.sync_playwright() as p:
                browser = p.chromium.launch(headless=True, args=["--no-sandbox"])
                ctx = browser.new_context(
                    user_agent="Mozilla/5.0 (iPhone; CPU iPhone OS 17_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.0 Mobile/15E148 Safari/604.1",
                    viewport={"width": 390, "height": 844}, locale="zh-CN")
                page = ctx.new_page()
                page.goto(f"https://www.bing.com/search?q={query}&setlang=zh-cn", timeout=20000)
                page.wait_for_timeout(3000)
                results = []
                for r in page.query_selector_all("li.b_algo h2 a"):
                    url = r.get_attribute("href") or ""
                    if url and not url.startswith("https://www.bing.com/ck/"):
                        results.append({"title": r.inner_text().strip(), "url": url})
                browser.close()
                if results: return results[:max_results]
        except: continue
    return [{"error": "Patchright和Playwright均不可用"}]

if __name__ == "__main__":
    import sys
    query = sys.argv[1] if len(sys.argv) > 1 else "南华大学 2025 转专业"
    for r in search_bing(query)[:5]:
        print(f"  📄 {r.get('title','?')[:50]}  🔗 {r.get('url','?')[:80]}")
```

## search_playwright.py
```python
"""
🌐 Playwright通用爬虫 v2
优化: 隐身配置 / 资源拦截加速 / 截图调试
"""
from playwright.sync_api import sync_playwright

def fetch_page(url: str, wait_selector: str = None, screenshot: bool = False, timeout: int = 20000) -> dict:
    """通用页面抓取，返回 {title, content, status, screenshot?}"""
    with sync_playwright() as p:
        browser = p.chromium.launch(headless=True, args=["--no-sandbox", "--disable-blink-features=AutomationControlled"])
        ctx = browser.new_context(user_agent="Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36",
                                  viewport={"width": 1920, "height": 1080}, locale="zh-CN")
        ctx.route("**/*.{png,jpg,jpeg,gif,webp}", lambda r: r.abort() if ctx else None)
        page = ctx.new_page()
        result = {"url": url, "status": "ok"}
        try:
            resp = page.goto(url, timeout=timeout, wait_until="domcontentloaded")
            result["http_status"] = resp.status if resp else 0
            page.wait_for_timeout(2000)
            result["title"] = page.title()
            result["content"] = page.content()
            if screenshot:
                import time
                page.screenshot(path=f"/workspace/scrapers/ss_{int(time.time())}.png", full_page=True)
        except Exception as e:
            result["status"] = "error"; result["error"] = str(e)
        finally:
            browser.close()
        return result
```

## demo_tls.py
```python
"""
🔒 curl_cffi TLS伪装 v2 — 多版本自动切换 + 反爬诊断
pip install curl_cffi
"""
from curl_cffi import requests

def fetch_with_tls(url: str) -> dict:
    """自动尝试多个TLS版本"""
    for imp in ["chrome124", "chrome123", "safari17_0", "edge124"]:
        try:
            r = requests.get(url, impersonate=imp, timeout=15)
            return {"status": r.status_code, "len": len(r.text), "source": imp}
        except: continue
    return {"status": 0, "source": "failed"}

def diagnose(url: str) -> list[dict]:
    """诊断反爬：裸curl → +UA → TLS伪装"""
    import subprocess
    results = []
    for name, cmd in [
        ("裸curl", ["curl", "-s", "-o", "/dev/null", "-w", "%{http_code}", url]),
        ("+UA", ["curl", "-s", "--compressed", "-L", url, "-H", "User-Agent: Mozilla/5.0", "-w", "%{http_code}"]),
    ]:
        r = subprocess.run(cmd, capture_output=True, text=True, timeout=10)
        results.append({"method": name, "status": r.stdout.strip()})
    results.append({"method": "TLS伪装", "status": str(fetch_with_tls(url).get("status", "?"))})
    return results

if __name__ == "__main__":
    import sys
    url = sys.argv[1] if len(sys.argv) > 1 else "https://www.baidu.com"
    for d in diagnose(url):
        print(f"  {d['method']:12s} → HTTP {d['status']}")
```

## debug_baidu.py
```python
"""
🔧 反爬调试 v2 — 多级诊断 + 自动建议
"""
import subprocess, re

def diagnose(url: str) -> list[dict]:
    """分级诊断：L0裸curl → L1+UA → L2全头 → 内容分析"""
    tests = []
    for level, cmd in [
        ("L0裸curl", ["curl", "-s", "-o", "/dev/null", "-w", "%{http_code}", url]),
        ("L1+UA", ["curl", "-s", "--compressed", "-L", url, "-H", "User-Agent: Mozilla/5.0", "-w", "\n%{http_code}"]),
    ]:
        r = subprocess.run(cmd, capture_output=True, text=True, timeout=10)
        code = r.stdout.strip().split('\n')[-1]
        tests.append({"level": level, "http": code})
    
    # 内容分析
    r = subprocess.run(["curl", "-s", "-L", url, "-H", "User-Agent: Mozilla/5.0"],
                      capture_output=True, text=True, timeout=10)
    if "captcha" in r.stdout or "验证" in r.stdout:
        tests.append({"level": "诊断", "http": "🛡️ CAPTCHA拦截"})
    elif len(r.stdout) < 200:
        tests.append({"level": "诊断", "http": "🛡️ 被拦截(内容过短)"})
    else:
        tests.append({"level": "诊断", "http": "✅ 可访问"})
    return tests

if __name__ == "__main__":
    import sys
    url = sys.argv[1] if len(sys.argv) > 1 else "https://www.baidu.com"
    for d in diagnose(url):
        print(f"  {d['level']:12s} → HTTP {d['http']}")
    # 自动建议
    if any('CAPTCHA' in str(d) for d in diagnose(url)):
        print(f"  💡 建议: 切SM神马搜索 或 Patchright+手机UA")
```

## lesson_all.py (五课整合)
```python
"""
📚 爬虫五课（整合优化版）
第一课: requests基础 → 第二课: 搜索 → 第三课: Playwright → 第四课: 会话池 → 第五课: 自适应降级
"""
import requests, re

# ── 第一课 ──
def lesson1_basic(url="https://httpbin.org/get"):
    try:
        r = requests.get(url, headers={"User-Agent": "Mozilla/5.0"}, timeout=10)
        return {"ok": r.ok, "status": r.status_code, "len": len(r.text)}
    except Exception as e: return {"error": str(e)}

# ── 第二课 ──
def lesson2_search(query):
    try:
        r = requests.get(f"https://www.baidu.com/s?wd={query}", 
                        headers={"User-Agent": "Mozilla/5.0"}, timeout=10)
        urls = re.findall(r'https?://[^"\'<>]+', r.text)
        return list(dict.fromkeys([u for u in urls if 'baidu.com/s' not in u]))[:15]
    except: return []

# ── 第三课 ──
def lesson3_playwright(url):
    try:
        from playwright.sync_api import sync_playwright
        with sync_playwright() as p:
            browser = p.chromium.launch(headless=True)
            page = browser.new_page()
            page.goto(url, timeout=15000)
            t, c = page.title(), page.content()
            browser.close()
            return {"title": t, "len": len(c)}
    except: return {"error": "pip install playwright"}

# ── 第四课 ──
class SessionPool:
    def __init__(self, n=3): self.avail, self.in_use, self.max = [], set(), n
    def acquire(self):
        s = self.avail.pop() if self.avail else self._create()
        self.in_use.add(id(s)); return s
    def release(self, s):
        self.in_use.discard(id(s)); self.avail.append(s)
    def _create(self):
        from playwright.sync_api import sync_playwright
        p = sync_playwright().start()
        return {"p": p, "b": p.chromium.launch(headless=True)}
    @property
    def stats(self): return f"活跃{len(self.in_use)} 空闲{len(self.avail)} 上限{self.max}"

# ── 第五课 ──
def lesson5_adaptive(url):
    """curl → requests → Playwright 自动降级"""
    import subprocess
    for name, fn in [
        ("curl", lambda: subprocess.run(["curl", "-s", "-L", url, "-H", "User-Agent: Mozilla/5.0"],
                                        capture_output=True, text=True, timeout=10).stdout),
        ("requests", lambda: requests.get(url, headers={"User-Agent": "Mozilla/5.0"}, timeout=10).text),
    ]:
        try:
            r = fn()
            if r and len(r) > 500: return {"source": name, "len": len(r)}
        except: continue
    return {"source": "failed"}

if __name__ == "__main__":
    print(f"第一课: {lesson1_basic()}")
    print(f"第二课: {len(lesson2_search('南华大学'))}链接")
    print(f"第三课: {lesson3_playwright('https://example.com')}")
    p = SessionPool(2)
    s = p.acquire(); print(f"第四课: {p.stats}"); p.release(s)
    print(f"第五课: {lesson5_adaptive('https://www.baidu.com')}")
```

---

## 📦 已整合文件清单

以下文件内容已全部包含在本文档中，可安全删除：

| 原文件 | 行数 | 状态 |
|:-------|:----:|:-----|
| `memory/爬虫知识体系.md`（旧） | 145 | ✅ 已整合到前半部分 |
| `爬虫工作流指南.md` | 6421 | ✅ 已整合到中间部分 |
| `scrapers/13个脚本` | 1052 | ✅ 已嵌入并优化 |
| **总计** | **~7618** | → **单个文件** |

---

*最后更新: 2026-07-24 | 单文件整合版 v6 | 代码模板 v2.0 优化版*
