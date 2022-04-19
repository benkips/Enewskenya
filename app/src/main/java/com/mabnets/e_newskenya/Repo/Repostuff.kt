package com.mabnets.e_newskenya.Repo


import androidx.room.withTransaction
import com.mabnets.e_newskenya.Network.ApiInterface
import com.mabnets.e_newskenya.Utils.NetworkBoundResource
import com.mabnets.e_newskenya.databasestuff.NewsDatabase
import kotlinx.coroutines.delay
import javax.inject.Inject
/*
class Repostuff @Inject constructor (private val apiInterface: ApiInterface):Baserepository(){
    suspend fun getSearchresults(query: String) =
        safeApiCall{apiInterface.getnews(query)}
}*/
class Repostuff @Inject constructor(
    private val apiInterface: ApiInterface,
    private  val db: NewsDatabase
){
    private  val mydataaDao=db.mydataDao()

    fun getnewsresults(newstation: String)= NetworkBoundResource(
        query = {
            mydataaDao.getAllnews(newstation)
        },
        fetch = {
            apiInterface.getnews(newstation)
        },
        saveFetchResult = {news->
            db.withTransaction {
                mydataaDao.deleteAllnews(newstation)
                mydataaDao.insertAllnews(news)
            }

        }
    )
}

