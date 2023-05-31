package com.example.phoenixnews.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.phoenixnews.utility.Contance.Companion.Class_name

@Entity(
    tableName = Class_name
)

data class Article(

    @PrimaryKey(autoGenerate = true)
    val id:Int?=null,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String,
    val urlToImage: String?
)