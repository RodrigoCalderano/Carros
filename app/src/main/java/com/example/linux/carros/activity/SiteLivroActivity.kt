package com.example.linux.carros.activity

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import com.example.linux.carros.R
import com.example.linux.carros.activity.dialogs.AboutDialog
import com.example.linux.carros.extensions.setupToolbar

class SiteLivroActivity : BaseActivity() {

    private val URL_SOBRE = "http://www.livroandroid.com.br/sobre.htm"
    private val URL_SOBRE_TESTE = "http://www.facebook.com.br"
    var webview: WebView? = null
    var progress: ProgressBar? = null
    var swipeToRefresh : SwipeRefreshLayout? = null

    override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        setContentView(R.layout.activity_site_livro)

        val actionBar = setupToolbar(R.id.toolbarCarros)
        actionBar.setDisplayHomeAsUpEnabled(true)

        webview = findViewById(R.id.webview)
        progress = findViewById(R.id.progress)

        setWebViewClient(webview)
        webview?.loadUrl(URL_SOBRE)

        swipeToRefresh = findViewById<SwipeRefreshLayout>(R.id.swipeToRefresh)
        swipeToRefresh?.setOnRefreshListener {
            webview?.reload()
        }
        swipeToRefresh?.setColorSchemeResources(
            R.color.refresh_progress_1,
            R.color.refresh_progress_2,
            R.color.refresh_progress_3)
    }

    private fun setWebViewClient(webView: WebView?) {
        webView?.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                progress?.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                progress?.visibility = View.INVISIBLE
                swipeToRefresh?.isRefreshing = false
            }

            // PEGA O FINAL DA DA URL E COMPARA COM SOBRE.HTM, SE FOR ABRE DIALOG
            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                val url = request?.url.toString()
                if (url.endsWith("sobre.htm")) {
                    AboutDialog.showAbout(supportFragmentManager)
                    return true
                }
                return super.shouldOverrideUrlLoading(view, request)
            }
        }
    }
}
