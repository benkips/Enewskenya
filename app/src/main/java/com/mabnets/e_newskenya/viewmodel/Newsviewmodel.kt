package com.mabnets.e_newskenya.viewmodel

import androidx.lifecycle.*
import com.mabnets.e_newskenya.Network.Resource
import com.mabnets.e_newskenya.Repo.Repostuff
import com.mabnets.e_newskenya.models.Mydata
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class Newsviewmodel @Inject constructor(private  val repostuff: Repostuff) : ViewModel(){

     lateinit var newsResponse: LiveData<Resource<List<Mydata>>>

    fun search(q:String){
        newsResponse=repostuff.getnewsresults(q).asLiveData()
    }
}