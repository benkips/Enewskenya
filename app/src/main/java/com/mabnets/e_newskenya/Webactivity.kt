package com.mabnets.e_newskenya

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.mabnets.e_newskenya.Utils.visible
import com.mabnets.e_newskenya.databinding.ActivityWebactivityBinding

class Webactivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityWebactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (binding.wvvs != null) {
            val url = intent.getStringExtra("url")
            binding.wvvs.settings.javaScriptEnabled = true
            binding.wvvs.webViewClient = WebViewClient()
            binding.wvvs.webChromeClient = WebChromeClient()
            if (url != null) {
                binding.wvvs.loadUrl(url)
            }

            binding.wvvs.webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    binding.pgbar.visible(true)
                    super.onPageStarted(view, url, favicon)

                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    try {
                        binding.pgbar.visible(false)
                    } catch (exception: Exception) {
                        exception.printStackTrace()
                    }

                    super.onPageFinished(view, url)
                }

                override fun onReceivedError(
                    view: WebView?,
                    errorCode: Int,
                    description: String?,
                    failingUrl: String?
                ) {
                    try {
                        binding.pgbar.visible(false)
                    } catch (exception: Exception) {
                        exception.printStackTrace()
                    }
                    val myerrorpage = "file:///android_asset/android/errorpage.html";
                    binding.wvvs.loadUrl(myerrorpage)
                    /*super.onReceivedError(view, errorCode, description, failingUrl)*/

                }
            }
            binding.wvvs.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_BACK && event.action == MotionEvent.ACTION_UP && binding.wvvs.canGoBack()) {
                    binding.wvvs.goBack()
                    return@OnKeyListener true
                }
                false
            })
            binding.fbtn.setOnClickListener {
                sharestuff(url!!)
            }
        }
    }
    private fun sharestuff(url:String){
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(
            Intent.EXTRA_TEXT,
            url
        )
        sendIntent.type = "text/plain"
        startActivity(sendIntent)
    }
}