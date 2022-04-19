package com.mabnets.e_newskenya.databasestuff

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mabnets.e_newskenya.models.Mydata
import kotlinx.coroutines.flow.Flow

@Dao
interface  MyDataDao {
    @Query("SELECT *  FROM  news  WHERE type=:search")
    fun getAllnews(search: String?): Flow<List<Mydata>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllnews(newz:List<Mydata>)

    @Query("DELETE FROM news  WHERE type=:search")
    suspend fun  deleteAllnews(search: String?)
}