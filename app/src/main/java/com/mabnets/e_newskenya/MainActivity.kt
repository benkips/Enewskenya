package com.mabnets.e_newskenya

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.facebook.ads.AudienceNetworkAds
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.messaging.FirebaseMessaging
import com.mabnets.e_newskenya.adapters.vpageradapter
import com.mabnets.e_newskenya.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val TOPIC="Alerts"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AudienceNetworkAds.initialize(this)
        val binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
        binding.viewPager.adapter = vpageradapter(this)
        TabLayoutMediator(binding.tablayout, binding.viewPager){ tab, position->
            when(position){
                0 -> {
                    tab.text = "Tuko news"
                }
                1 -> {
                    tab.text = "Kenyans"
                }
                2-> {
                    tab.text = "The star"
                }


            }

        }.attach()
    }
}