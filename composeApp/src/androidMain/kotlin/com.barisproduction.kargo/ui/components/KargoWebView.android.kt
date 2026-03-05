package com.barisproduction.kargo.ui.components

import android.graphics.Bitmap
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
actual fun KargoWebView(
    url: String,
    modifier: Modifier,
    onLoadingStateChanged: (Boolean) -> Unit,
    onError: (String) -> Unit,
    js: String
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            WebView(context).apply {
                webViewClient = object : WebViewClient() {
                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        super.onPageStarted(view, url, favicon)
                        onLoadingStateChanged(true)
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        onLoadingStateChanged(false)

                        val cleanJs = js
                            .replace("javascript:", "") // javascript takısını kaldır
                            .replace("\n", " ")         // Alt satıra geçişleri (enter) boşluğa çevir
                            .replace("\r", "")          // Varsa carriage return karakterlerini temizle

                       // val darkModeJs = "(function(){document.documentElement.style.filter='invert(1) hue-rotate(180deg)';var media=document.querySelectorAll('img, picture, video, svg');media.forEach(function(el){el.style.filter='invert(1) hue-rotate(180deg)';});document.body.style.backgroundColor='#121212';})();"
                        println(cleanJs)
                        view?.evaluateJavascript(cleanJs, null)
                    }

                    override fun onReceivedError(
                        view: WebView?,
                        request: WebResourceRequest?,
                        error: WebResourceError?
                    ) {
                        super.onReceivedError(view, request, error)
                        onError(error?.description?.toString() ?: "Unknown Error")
                        onLoadingStateChanged(false)
                    }
                }
                settings.javaScriptEnabled = true
                loadUrl(url)
            }
        },
        update = { webView ->
            if (webView.url != url) {
                webView.loadUrl(url)
            }
        }
    )
}
