package com.mabnets.e_newskenya.adapters

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.chayangkoon.champ.linkpreview.LinkPreview
import com.google.android.ads.nativetemplates.NativeTemplateStyle
import com.google.android.ads.nativetemplates.TemplateView
import com.google.android.gms.ads.*
import com.mabnets.e_newskenya.R
import com.mabnets.e_newskenya.Webactivity
import com.mabnets.e_newskenya.databinding.PreviewBinding
import com.mabnets.e_newskenya.models.Mydata


class Newsadapter(private val newsstuff:List<Mydata>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val AD_TYPE = 1
    private val DEFAULT_VIEW_TYPE = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == AD_TYPE) {
            val view = LayoutInflater.from(parent.context).inflate(
                R.layout.banner_ad,
                parent,
                false
            )
            return adholderc(view)
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
            binding.apply {
                Glide.with(itemView)
                    .load(news.imagelink)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(imagePost)

                title.text = news.title
                description.text = news.description
            }

            itemView.setOnClickListener {
                val intent = Intent(binding.root.context, Webactivity::class.java)
                intent.putExtra("url", news.links)
                binding.root.context.startActivity(intent);
            }
        }
    }

    inner class adholderc(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var adView: AdView
        private lateinit var linearbanner: LinearLayout

        init {
            adView = AdView(itemView.context)
            linearbanner = itemView.findViewById(R.id.banner_container)
            linearbanner.addView(adView)
            adView.adUnitId = "ca-app-pub-4814079884774543/2398966193"

            adView.adSize = AdSize.SMART_BANNER
            val adRequest = AdRequest
                .Builder()
                .build()
            // Start loading the ad in the background.
            adView.loadAd(adRequest)
        }


    }

}