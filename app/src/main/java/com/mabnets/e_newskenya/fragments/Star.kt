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
import com.mabnets.e_newskenya.databinding.FragmentStarBinding
import com.mabnets.e_newskenya.viewmodel.Newsviewmodel
import dagger.hilt.android.AndroidEntryPoint
import www.digitalexperts.church_tracker.Network.Resource


@AndroidEntryPoint
class Star : Fragment(R.layout.fragment_star) {
   private  var _binding : FragmentStarBinding?=null
   private val binding get() = _binding!!

   private  val viewmodel by viewModels<Newsviewmodel>()
   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      _binding= FragmentStarBinding.bind(view)

      binding.pgbar.visible(false)
      getcontents("star")
      viewmodel.newsResponse.observe(viewLifecycleOwner, Observer {
         binding.pgbar.visible(it is Resource.Loading)
         when (it) {
            is Resource.Success -> {
               binding.rvstar.also {rv->
                  rv.layoutManager = LinearLayoutManager(requireContext())
                  rv.setHasFixedSize(true)
                  rv.adapter= Newsadapter(it.value)
               }

            }
            is Resource.Failure -> handleApiError(it) {  getcontents("star")}
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