package com.mabnets.e_newskenya

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.mabnets.e_newskenya.Utils.visible
import com.mabnets.e_newskenya.databinding.ActivityWebactivityBinding
import java.io.ByteArrayInputStream
import java.io.InputStream


class Webactivity : AppCompatActivity() {
    private lateinit var adView: AdView
    private lateinit var binding:ActivityWebactivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = ActivityWebactivityBinding.inflate(layoutInflater)
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
        adView = AdView(this)
        binding.bannerContainertwo.addView(adView)
        adView.adUnitId = "ca-app-pub-4814079884774543/2398966193"

        adView.adSize = adSize
        val adRequest = AdRequest
            .Builder()
            .build()
        // Start loading the ad in the background.
        adView.loadAd(adRequest)
    }
    private val adSize: AdSize
        get() {
            val display =this.windowManager!!.defaultDisplay
            val outMetrics = DisplayMetrics()
            display.getMetrics(outMetrics)

            val density = outMetrics.density

            var adWidthPixels = binding.bannerContainertwo.width.toFloat()
            if (adWidthPixels == 0f) {
                adWidthPixels = outMetrics.widthPixels.toFloat()
            }

            val adWidth = (adWidthPixels / density).toInt()
            return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth)
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