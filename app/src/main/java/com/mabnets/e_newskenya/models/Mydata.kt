package com.mabnets.e_newskenya.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="news")
data class Mydata(
    @PrimaryKey val id: Int,
    val links: String,
    val title: String,
    val type: String,
    val description: String,
    val imagelink:String

)