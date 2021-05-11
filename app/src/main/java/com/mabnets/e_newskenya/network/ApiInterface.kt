package www.digitalexperts.church_tracker.Network

import com.mabnets.kenyanelitenews.models.News
import retrofit2.http.*


interface ApiInterface {
    companion object{
        const val BASE_URL="https://allcollections.howtoinkenya.co.ke/"
    }
    @GET("newstuff.php")
    suspend  fun getnews(
        @Query("data")dataz:String?,

        ): News

}