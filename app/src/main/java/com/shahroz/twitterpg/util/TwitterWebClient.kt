package com.shahroz.twitterpg.util

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

@SuppressLint("SetJavaScriptEnabled")
@Suppress("OverridingDeprecatedMember")
class TwitterWebClient(
    context: Context,
    url: String,
    private val getOAuthAccessToken: (oauthVerifier: String) -> Unit
) : WebViewClient() {

    var twitterDialog: Dialog = Dialog(context)

    private val callbackUrl: String = "http://shoxiiapps.apps"

    init {
        val webView = WebView(context)
        webView.isVerticalScrollBarEnabled = false
        webView.isHorizontalScrollBarEnabled = false
        webView.webViewClient = this
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(url)
        twitterDialog.setContentView(webView)
        twitterDialog.show()
    }

    override fun shouldOverrideUrlLoading(
        view: WebView?,
        request: WebResourceRequest?
    ): Boolean {
        if (request?.url.toString().startsWith(callbackUrl)) {
            Log.d("Authorization URL: ", request?.url.toString())
            handleUrl(request?.url.toString())

            // Close the dialog after getting the oauth_verifier
            if (request?.url.toString().contains(callbackUrl)) {
                twitterDialog.dismiss()
            }
            return true
        }
        return false
    }

    // Get the oauth_verifier
    private fun handleUrl(url: String) {
        val uri = Uri.parse(url)
        val oauthVerifier = uri.getQueryParameter("oauth_verifier") ?: ""
        getOAuthAccessToken.invoke(oauthVerifier)
    }
}