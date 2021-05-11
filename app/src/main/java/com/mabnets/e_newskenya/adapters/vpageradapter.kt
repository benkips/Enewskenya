package com.mabnets.e_newskenya.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mabnets.e_newskenya.MainActivity
import com.mabnets.e_newskenya.fragments.Kenyans
import com.mabnets.e_newskenya.fragments.Star
import com.mabnets.e_newskenya.fragments.Tuko



class vpageradapter(fragmentActivity: MainActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> Tuko()
            1-> Kenyans()
            else -> {
                Star()
            }
        }
    }

    override fun getItemCount(): Int {
        return 3
    }
}

