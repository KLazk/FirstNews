package com.shokworks.firstnews.dbRoom

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TFavNews(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var author: String,
    var content: String,
    var description: String,
    var publishedAt: String,
    var title: String,
    var url: String,
    var urlToImage: String)