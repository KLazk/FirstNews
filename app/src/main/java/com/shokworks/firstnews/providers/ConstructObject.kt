package com.shokworks.firstnews.providers

import com.shokworks.firstnews.dbRoom.TFavNews
import com.shokworks.firstnews.network.entitys.Article
import javax.inject.Inject

class ConstructObject @Inject constructor() {

    fun addTFavNews(article: Article): TFavNews {
       return TFavNews(
               author = article.author,
               content = article.content,
               description = article.description,
               publishedAt = article.publishedAt,
               title = article.title,
               url = article.url,
               urlToImage = article.urlToImage
        )
    }
}