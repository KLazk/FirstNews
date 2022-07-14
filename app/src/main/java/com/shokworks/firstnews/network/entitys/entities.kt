package com.shokworks.firstnews.network.entitys

data class News(
    var status: String,
    var totalResults: Int,
    var articles: List<Article>
)

data class Article(
    var author: String,
    var content: String,
    var description: String,
    var publishedAt: String,
    var title: String,
    var url: String,
    var urlToImage: String,
    var isFav: Boolean = false
)