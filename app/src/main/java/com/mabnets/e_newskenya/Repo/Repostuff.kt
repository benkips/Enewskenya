package com.mabnets.e_newskenya.Repo


import www.digitalexperts.church_tracker.Network.ApiInterface
import javax.inject.Inject

class Repostuff @Inject constructor (private val apiInterface: ApiInterface):Baserepository(){
    suspend fun getSearchresults(query: String) =
        safeApiCall{apiInterface.getnews(query)}
}
