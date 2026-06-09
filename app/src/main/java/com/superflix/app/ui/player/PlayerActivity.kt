package com.superflix.app.ui.player

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.superflix.app.R
import com.superflix.app.data.api.SuperFlixApi

class PlayerActivity : AppCompatActivity() {
    
    private lateinit var webView: WebView
    private val api = SuperFlixApi()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        
        val type = intent.getStringExtra("type") ?: "filme"
        val id = intent.getStringExtra("id") ?: ""
        
        webView = findViewById(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true
        webView.webViewClient = WebViewClient()
        
        val url = api.getPlayerUrl(type, id)
        webView.loadUrl(url)
    }
    
    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}