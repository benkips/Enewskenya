package com.mabnets.e_newskenya.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.mabnets.e_newskenya.R
import com.mabnets.e_newskenya.adapters.vpageradapter
import com.mabnets.e_newskenya.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         _binding = FragmentHomeBinding.bind(view)
        //binding.viewPager.setAdapter(new vpageradapter(getParentFragment()));
        binding.viewPager.adapter = vpageradapter(requireParentFragment())
        TabLayoutMediator(binding.tablayout, binding.viewPager){ tab, position->
            when(position){
                0 -> {
                    tab.text = "Tuko news"
                }
                1 -> {
                    tab.text = "The kenyan news"
                }
                2-> {
                    tab.text = "The star news"
                }


            }

        }.attach()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}