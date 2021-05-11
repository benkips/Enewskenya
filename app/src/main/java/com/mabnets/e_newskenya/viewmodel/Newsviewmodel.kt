package com.mabnets.e_newskenya.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mabnets.e_newskenya.Repo.Repostuff
import com.mabnets.kenyanelitenews.models.News
import kotlinx.coroutines.launch
import www.digitalexperts.church_tracker.Network.Resource

class Newsviewmodel @ViewModelInject constructor(private  val repostuff: Repostuff) : ViewModel(){

    private val _newsResponse: MutableLiveData<Resource<News>> = MutableLiveData()
     val newsResponse: LiveData<Resource<News>>
         get() = _newsResponse
     fun search(
         query: String
     ) = viewModelScope.launch {
         _newsResponse.value = Resource.Loading
         _newsResponse.value=repostuff.getSearchresults(query)
     }
}