package me.rerere.rikkahub.ui.components.richtext

import android.graphics.BitmapFactory
import android.util.Base64
import android.webkit.JavascriptInterface
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dokar.sonner.ToastType
import me.rerere.hugeicons.HugeIcons
import me.rerere.hugeicons.stroke.Download01
import me.rerere.hugeicons.stroke.View
import me.rerere.rikkahub.R
import me.rerere.rikkahub.Screen
import me.rerere.rikkahub.ui.components.webview.WebView
import me.rerere.rikkahub.ui.components.webview.WebViewContentCache
import me.rerere.rikkahub.ui.components.webview.rememberWebViewState
import me.rerere.rikkahub.ui.context.LocalNavController
import me.rerere.rikkahub.ui.context.LocalToaster
import me.rerere.rikkahub.ui.theme.LocalDarkMode
import me.rerere.rikkahub.utils.escapeHtml
import me.rerere.rikkahub.utils.exportImage
import me.rerere.rikkahub.utils.toCssHex

@Composable
fun Mermaid(
    code: String,
    modifier: Modifier = Modifier,
) {
    val colorScheme = MaterialTheme.colorScheme
    val darkMode = LocalDarkMode.current
    val context = LocalContext.current
    val activity = LocalActivity.current
    val toaster = LocalToaster.current
    val navController = LocalNavController.current

    val jsInterface = remember {
        MermaidInterface(
            onExportImage = { base64Image ->
                runCatching {
                    activity?.let {
                        try {
                            val imageBytes = Base64.decode(base64Image, Base64.DEFAULT)
                            val bitmap =
                                BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                            context.exportImage(
                                it,
                                bitmap,
                                "mermaid_${System.currentTimeMillis()}.png"
                            )
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    toaster.show(
                        context.getString(R.string.mermaid_export_success),
                        type = ToastType.Success
                    )
                }.onFailure {
                    it.printStackTrace()
                    toaster.show(
                        context.getString(R.string.mermaid_export_failed),
                        type = ToastType.Error
                    )
                }
            }
        )
    }

    val html = remember(code, colorScheme, darkMode) {
        buildMermaidHtml(
            code = code,
            colorScheme = colorScheme,
        )
    }

    // 动态高度：Mermaid 渲染完成后由 JS 汇报实际内容高度
    var renderedHeight by remember { mutableStateOf(200.dp) }

    val renderInterface = remember {
        MermaidRenderInterface(
            onRendered = { heightPx ->
                val density = LocalDensity.current
                renderedHeight = with(density) { heightPx.toDp() + 16.dp }
            }
        )
    }

    val webViewState = rememberWebViewState(
        data = html,
        mimeType = "text/html",
        encoding = "UTF-8",
        interfaces = mapOf(
            "AndroidInterface" to jsInterface,
            "RenderInterface" to renderInterface,
        ),
        settings = {
            builtInZoomControls = true
            displayZoomControls = false
            useWideViewPort = true
            loadWithOverviewMode = true
        }
    )

    Column(
        modifier = modifier
    ) {
        WebView(
            state = webViewState,
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .height(renderedHeight),
        )

        if (activity != null) {
            Row(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                IconButton(
                    onClick = {
                        val contentId = WebViewContentCache.store(context.cacheDir, html)
                        navController.navigate(Screen.WebView(contentId = contentId))
                    },
                ) {
                    Icon(
                        HugeIcons.View,
                        contentDescription = "Preview"
                    )
                }
                IconButton(
                    onClick = {
                        webViewState.webView?.evaluateJavascript(
                            "exportSvgToPng();",
                            null
                        )
                    },
                ) {
                    Icon(
                        HugeIcons.Download01,
                        contentDescription = stringResource(R.string.mermaid_export)
                    )
                }
            }
        }
    }
}

private class MermaidInterface(
    private val onExportImage: (String) -> Unit
) {
    @JavascriptInterface
    fun exportImage(base64Image: String) {
        onExportImage(base64Image)
    }
}

/** 接收 Mermaid 渲染完成后内容高度回调，用于动态调整 WebView 高度 */
private class MermaidRenderInterface(
    private val onRendered: (Int) -> Unit
) {
    @JavascriptInterface
    fun onRendered(height: Int) {
        onRendered(height)
    }
}

private fun buildMermaidHtml(
    code: String,
    colorScheme: ColorScheme,
): String {
    val primaryColor = colorScheme.primaryContainer.toCssHex()
    val secondaryColor = colorScheme.secondaryContainer.toCssHex()
    val tertiaryColor = colorScheme.tertiaryContainer.toCssHex()
    val background = colorScheme.background.toCssHex()
    val surface = colorScheme.surface.toCssHex()
    val onPrimary = colorScheme.onPrimaryContainer.toCssHex()
    val onSecondary = colorScheme.onSecondaryContainer.toCssHex()
    val onTertiary = colorScheme.onTertiaryContainer.toCssHex()
    val onBackground = colorScheme.onBackground.toCssHex()
    val errorColor = colorScheme.error.toCssHex()
    val onErrorColor = colorScheme.onError.toCssHex()

    return """
        <!DOCTYPE html>
        <html>
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=1024">
            <script src="https://cdn.jsdelivr.net/npm/mermaid@11/dist/mermaid.min.js"></script>
            <style>
                body {
                    margin: 0;
                    padding: 0;
                    display: flex;
                    justify-content: center;
                    background-color: ${background};
                }
                .mermaid {
                    padding: 8px;
                    width: fit-content;
                    min-width: 100%;
                }
                .mermaid svg {
                    background: transparent !important;
                }
            </style>
        </head>
        <body>
            <pre class="mermaid">
                ${code.escapeHtml()}
            </pre>
            <script>
              // 渲染完成后通知 Android 端实际高度
              function reportHeight() {
                const svg = document.querySelector('.mermaid svg');
                if (svg) {
                  const height = svg.getBoundingClientRect().height;
                  RenderInterface.onRendered(Math.ceil(height));
                }
              }

              // 监听 mermaid 渲染完成事件
              document.addEventListener('DOMContentLoaded', function() {
                // 轮询检测 mermaid 是否渲染完成（startOnLoad 异步渲染）
                var checkCount = 0;
                var checkTimer = setInterval(function() {
                  var svg = document.querySelector('.mermaid svg');
                  if (svg) {
                    clearInterval(checkTimer);
                    reportHeight();
                  }
                  checkCount++;
                  if (checkCount > 60) { // 最多等 30 秒
                    clearInterval(checkTimer);
                    reportHeight();
                  }
                }, 500);
              });

              mermaid.initialize({
                    startOnLoad: true,
                    theme: 'base',
                    themeVariables: {
                        primaryColor: '${primaryColor}',
                        primaryTextColor: '${onPrimary}',
                        primaryBorderColor: '${primaryColor}',

                        secondaryColor: '${secondaryColor}',
                        secondaryTextColor: '${onSecondary}',
                        secondaryBorderColor: '${secondaryColor}',

                        tertiaryColor: '${tertiaryColor}',
                        tertiaryTextColor: '${onTertiary}',
                        tertiaryBorderColor: '${tertiaryColor}',

                        background: '${background}',
                        mainBkg: '${primaryColor}',
                        secondBkg: '${secondaryColor}',

                        lineColor: '${onBackground}',
                        textColor: '${onBackground}',

                        nodeBkg: '${surface}',
                        nodeBorder: '${primaryColor}',
                        clusterBkg: '${surface}',
                        clusterBorder: '${primaryColor}',

                        actorBorder: '${primaryColor}',
                        actorBkg: '${surface}',
                        actorTextColor: '${onBackground}',
                        actorLineColor: '${primaryColor}',

                        taskBorderColor: '${primaryColor}',
                        taskBkgColor: '${primaryColor}',
                        taskTextLightColor: '${onPrimary}',
                        taskTextDarkColor: '${onBackground}',

                        labelColor: '${onBackground}',
                        errorBkgColor: '${errorColor}',
                        errorTextColor: '${onErrorColor}'
                    }
              });

              window.exportSvgToPng = function() {
                try {
                    const svgElement = document.querySelector('.mermaid svg');
                    if (!svgElement) {
                        AndroidInterface.exportImage('');
                        return;
                    }

                    const canvas = document.createElement('canvas');
                    const ctx = canvas.getContext('2d');

                    const svgRect = svgElement.getBoundingClientRect();
                    const width = svgRect.width;
                    const height = svgRect.height;

                    const scaleFactor = window.devicePixelRatio * 2;
                    canvas.width = width * scaleFactor;
                    canvas.height = height * scaleFactor;

                    const svgXml = new XMLSerializer().serializeToString(svgElement);
                    const svgBase64 = btoa(unescape(encodeURIComponent(svgXml)));

                    const img = new Image();
                    img.onload = function() {
                        ctx.fillStyle = '${background}';
                        ctx.fillRect(0, 0, canvas.width, canvas.height);
                        ctx.drawImage(img, 0, 0, canvas.width, canvas.height);

                        ctx.font = '14px Arial';
                        ctx.fillStyle = '${onBackground}';
                        ctx.fillText('rikka-ai.com', 20, canvas.height - 10);

                        const pngBase64 = canvas.toDataURL('image/png').split(',')[1];
                        AndroidInterface.exportImage(pngBase64);
                    };
                    img.onerror = function(e) {
                        AndroidInterface.exportImage('');
                    }
                    img.src = 'data:image/svg+xml;base64,' + svgBase64;
                } catch (e) {
                    AndroidInterface.exportImage('');
                }
              };
            </script>
        </body>
        </html>
    """.trimIndent()
}
