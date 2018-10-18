package com.example.linux.carros.activity

import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import com.example.linux.carros.R
import com.example.linux.carros.extensions.setupToolbar

class SiteLivroActivity : BaseActivity() {

    private val URL_SOBRE = "http://www.livroandroid.com.br/sobre.htm"
    var webview: WebView? = null
    var progress: ProgressBar? = null

    override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        setContentView(R.layout.activity_site_livro)

        val actionBar = setupToolbar(R.id.toolbarCarros)
        actionBar.setDisplayHomeAsUpEnabled(true)

        webview = findViewById(R.id.webview)
        progress = findViewById(R.id.progress)

        setWebViewClient(webview)
        webview?.loadUrl(URL_SOBRE)
    }

    private fun setWebViewClient(webView: WebView?) {
        webView?.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                progress?.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                progress?.visibility = View.INVISIBLE
            }
        }
    }
}
