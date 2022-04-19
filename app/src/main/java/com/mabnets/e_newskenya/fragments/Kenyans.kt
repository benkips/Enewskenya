package com.mabnets.e_newskenya.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mabnets.e_newskenya.Network.Resource
import com.mabnets.e_newskenya.R
import com.mabnets.e_newskenya.Utils.visible
import com.mabnets.e_newskenya.adapters.Newsadapter
import com.mabnets.e_newskenya.databinding.FragmentKenyansBinding
import com.mabnets.e_newskenya.viewmodel.Newsviewmodel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class Kenyans : Fragment(R.layout.fragment_kenyans) {
    private var _binding: FragmentKenyansBinding? = null
    private val binding get() = _binding!!

    private val viewmodel by viewModels<Newsviewmodel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentKenyansBinding.bind(view)

        binding.pgbar.visible(false)
        getcontents("kenyans")

        binding.apply {
            rvkenyans.also { rv ->
                rv.layoutManager = LinearLayoutManager(requireContext())
                rv.setHasFixedSize(true)
                viewmodel.newsResponse.observe(viewLifecycleOwner) { newx ->
                    rv.adapter = Newsadapter(newx.data!!)

                    pgbar.isVisible=newx is Resource.Loading && newx.data.isNullOrEmpty()
                    tvError.isVisible=newx is Resource.Error && newx.data.isNullOrEmpty()
                    tvError.text=newx.error?.localizedMessage
                }
            }

        }


    }

    private fun getcontents(x: String) {
        viewmodel.search(x)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}