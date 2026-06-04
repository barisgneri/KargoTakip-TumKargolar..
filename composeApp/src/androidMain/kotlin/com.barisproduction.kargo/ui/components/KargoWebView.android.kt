package com.barisproduction.kargo.ui.components

import android.graphics.Bitmap
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
actual fun KargoWebView(
    url: String,
    modifier: Modifier,
    onLoadingStateChanged: (Boolean) -> Unit,
    onError: (Int) -> Unit,
    js: String,
    injectJs: String?,
    onJsInjected: () -> Unit
) {
    var lastLoadedUrl by remember { mutableStateOf("") }

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

                        view?.evaluateJavascript(cleanJs, null)
                    }

                    override fun onReceivedError(
                        view: WebView?,
                        request: WebResourceRequest?,
                        error: WebResourceError?
                    ) {
                        super.onReceivedError(view, request, error)
                        onError(error?.errorCode ?: 404)
                        onLoadingStateChanged(false)
                    }
                }
                settings.javaScriptEnabled = true
            }
        },
        update = { webView ->
            if (lastLoadedUrl != url) {
                lastLoadedUrl = url
                webView.loadUrl(url)
            }
            injectJs?.let {
                webView.evaluateJavascript(it, null)
                onJsInjected()
            }
        }
    )
}
