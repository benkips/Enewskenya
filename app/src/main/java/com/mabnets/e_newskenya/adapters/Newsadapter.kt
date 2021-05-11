package com.mabnets.e_newskenya.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chayangkoon.champ.linkpreview.LinkPreview
import com.facebook.ads.*
import com.github.satoshun.coroutine.autodispose.view.autoDisposeScope
import com.mabnets.e_newskenya.R
import com.mabnets.e_newskenya.Webactivity
import com.mabnets.e_newskenya.databinding.PreviewBinding
import com.mabnets.kenyanelitenews.models.Mydata
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class Newsadapter(private val newsstuff: ArrayList<Mydata>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val AD_TYPE = 1
    private val DEFAULT_VIEW_TYPE = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == AD_TYPE) {
            val view = LayoutInflater.from(parent.context).inflate(
                R.layout.templatefile,
                parent,
                false
            )
            return adholderfb(view)
        } else {
            val binding = PreviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return NewsViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder !is NewsViewHolder) {
            return
        }
        val itemPosition = position - position / 4
        val currentitem = newsstuff[itemPosition];
        if (currentitem != null) {
            holder.bind(currentitem)
        }
    }


    override fun getItemCount(): Int {
        var itemCount: Int = newsstuff.size
        itemCount += itemCount / 4
        return itemCount
    }

    override fun getItemViewType(position: Int): Int {
        return if (position > 1 && position % 4 == 0) {
            AD_TYPE
        } else DEFAULT_VIEW_TYPE
    }

    inner class NewsViewHolder(private val binding: PreviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val linkPreview = LinkPreview.Builder().build()



        fun bind(news: Mydata) {
            //binding.description.text = news.
            val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                Toast.makeText(
                    binding.root.context,
                    throwable.message.orEmpty(),
                    Toast.LENGTH_SHORT
                ).show()
            }
            itemView.setOnClickListener {
                val intent = Intent(binding.root.context, Webactivity::class.java)
                intent.putExtra("url", news.links)
                binding.root.context.startActivity(intent);
            }
            binding.root.autoDisposeScope.launch(exceptionHandler) {
                val linkContent = withContext(Dispatchers.IO) {
                    linkPreview.loadPreview(news.links, {
                        binding.apply {
                            Glide.with(itemView)
                                .load(it.imageUrl)
                                .centerCrop()
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .error(R.drawable.ic_error)
                                .into(imagePost)

                            title.text = it.title
                            description.text = it.description

                        }
                    }, {})
                }

            }
        }
    }

    inner class adholderfb(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var nativeAdLayout: NativeAdLayout? = null
        private var adView: LinearLayout? = null
        private val nativeAd: NativeAd?
        private fun inflateAd(nativeAd: NativeAd) {
            nativeAd.unregisterView()

            // Add the Ad view into the ad container.
            nativeAdLayout = itemView.findViewById(R.id.native_ad_container)
            val inflater = LayoutInflater.from(itemView.context)
            // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
            adView =
                inflater.inflate(R.layout.native_ad_layout, nativeAdLayout, false) as LinearLayout
            nativeAdLayout!!.addView(adView)

            // Add the AdOptionsView
            val adChoicesContainer = itemView.findViewById<LinearLayout>(R.id.ad_choices_container)
            val adOptionsView = AdOptionsView(itemView.context, nativeAd, nativeAdLayout)
            adChoicesContainer.removeAllViews()
            adChoicesContainer.addView(adOptionsView, 0)

            // Create native UI using the ad metadata.
            val nativeAdIcon: AdIconView = adView!!.findViewById(R.id.native_ad_icon)
            val nativeAdTitle = adView!!.findViewById<TextView>(R.id.native_ad_title)
            val nativeAdMedia: MediaView = adView!!.findViewById(R.id.native_ad_media)
            val nativeAdSocialContext =
                adView!!.findViewById<TextView>(R.id.native_ad_social_context)
            val nativeAdBody = adView!!.findViewById<TextView>(R.id.native_ad_body)
            val sponsoredLabel = adView!!.findViewById<TextView>(R.id.native_ad_sponsored_label)
            val nativeAdCallToAction = adView!!.findViewById<Button>(R.id.native_ad_call_to_action)

            // Set the Text.
            nativeAdTitle.text = nativeAd.advertiserName
            nativeAdBody.text = nativeAd.adBodyText
            nativeAdSocialContext.text = nativeAd.adSocialContext
            nativeAdCallToAction.visibility =
                if (nativeAd.hasCallToAction()) View.VISIBLE else View.INVISIBLE
            nativeAdCallToAction.text = nativeAd.adCallToAction
            sponsoredLabel.text = nativeAd.sponsoredTranslation

            // Create a list of clickable views
            val clickableViews: MutableList<View> = java.util.ArrayList()
            clickableViews.add(nativeAdTitle)
            clickableViews.add(nativeAdCallToAction)

            // Register the Title and CTA button to listen for clicks.
            nativeAd.registerViewForInteraction(
                adView,
                nativeAdMedia,
                nativeAdIcon,
                clickableViews
            )
        }

        init {
            AudienceNetworkAds.initialize(itemView.context)
            nativeAd = NativeAd(itemView.context, "684337052511955_684485762497084")
            nativeAd.setAdListener(object : NativeAdListener {
                override fun onMediaDownloaded(ad: Ad) {}
                override fun onError(ad: Ad, adError: AdError) {}
                override fun onAdLoaded(ad: Ad) {
                    // Race condition, load() called again before last ad was displayed
                    if (nativeAd == null || nativeAd !== ad) {
                        return
                    }
                    // Inflate Native Ad into Container
                    inflateAd(nativeAd)
                }

                override fun onAdClicked(ad: Ad) {}
                override fun onLoggingImpression(ad: Ad) {}
            })

            // Request an ad
            nativeAd.loadAd()
        }
    }


}