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

    fun addNews(tFavNews: TFavNews): Article {
        return Article(
            author = tFavNews.author,
            content = tFavNews.content,
            description = tFavNews.description,
            publishedAt = tFavNews.publishedAt,
            title = tFavNews.title,
            url = tFavNews.url,
            urlToImage = tFavNews.urlToImage,
            isFav = true
        )
    }
}