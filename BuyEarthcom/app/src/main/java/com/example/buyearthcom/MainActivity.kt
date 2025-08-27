package com.example.buyearthcom

import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val webView = findViewById<WebView>(R.id.webview)

        // 🌐 Ensure links open inside the app
        webView.webViewClient = WebViewClient()

        // ✅ Enable JavaScript
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true

        // ✅ Ensure site fits properly
        webSettings.loadWithOverviewMode = true
        webSettings.useWideViewPort = true
        webSettings.domStorageEnabled = true
        webSettings.builtInZoomControls = false
        webSettings.displayZoomControls = false

        // Load your React website
        webView.loadUrl("https://candidate-00-x-ecoworldbuy-module-l.vercel.app/")  // replace with actual URL
    }
}
