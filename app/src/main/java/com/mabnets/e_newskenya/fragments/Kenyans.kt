package com.mabnets.e_newskenya.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mabnets.e_newskenya.R
import com.mabnets.e_newskenya.Utils.handleApiError
import com.mabnets.e_newskenya.Utils.visible
import com.mabnets.e_newskenya.adapters.Newsadapter
import com.mabnets.e_newskenya.databinding.FragmentKenyansBinding
import com.mabnets.e_newskenya.viewmodel.Newsviewmodel
import dagger.hilt.android.AndroidEntryPoint
import www.digitalexperts.church_tracker.Network.Resource


@AndroidEntryPoint
class Kenyans : Fragment(R.layout.fragment_kenyans) {
    private  var _binding : FragmentKenyansBinding?=null
    private val binding get() = _binding!!

    private  val viewmodel by viewModels<Newsviewmodel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding= FragmentKenyansBinding.bind(view)

        binding.pgbar.visible(false)
        getcontents("kenyans")
        viewmodel.newsResponse.observe(viewLifecycleOwner, Observer {
            binding.pgbar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    binding.rvkenyans.also {rv->
                        rv.layoutManager = LinearLayoutManager(requireContext())
                        rv.setHasFixedSize(true)
                        rv.adapter= Newsadapter(it.value)
                    }

                }
                is Resource.Failure -> handleApiError(it) {  getcontents("kenyans")}
            }
        })


    }
    private  fun getcontents(x:String){
        viewmodel.search(x)
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}