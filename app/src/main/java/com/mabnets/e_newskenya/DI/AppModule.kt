package com.mabnets.e_newskenya.DI

import android.app.Application
import androidx.room.Room
import com.mabnets.e_newskenya.Network.ApiInterface
import com.mabnets.e_newskenya.databasestuff.NewsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okclients = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        return Retrofit.Builder().client(okclients)
            .baseUrl(ApiInterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Provides
    @Singleton
    fun providesApiInterface(retrofit: Retrofit):ApiInterface=
        retrofit.create(ApiInterface::class.java)

    @Provides
    @Singleton
    fun providesDatabase(app: Application) : NewsDatabase =
        Room.databaseBuilder(app,NewsDatabase::class.java,"news_db")
            .build()

}