package com.mabnets.e_newskenya.Network

import com.mabnets.e_newskenya.models.Mydata
import retrofit2.http.*


interface ApiInterface {
    companion object{
        const val BASE_URL="https://allcollections.howtoinkenya.co.ke/"
    }
    @GET("allnews")
    suspend  fun getnews(
        @Query("data")dataz:String?,

        ): List<Mydata>

}