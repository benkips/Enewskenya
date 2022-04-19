package com.mabnets.e_newskenya.databasestuff

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mabnets.e_newskenya.models.Mydata


@Database(entities = [Mydata::class],version=1)
abstract  class NewsDatabase : RoomDatabase() {

    abstract fun mydataDao(): MyDataDao
}