package com.shokworks.firstnews.network

import com.shokworks.firstnews.network.entitys.News
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    /** EndPoint para obtener las noticias */
    @GET("everything")
    suspend fun getNews(
        @Query(value = "q") q: String,
        @Query(value = "from") from: String,
        @Query(value = "to") to: String,
        @Query(value = "sortBy") sortBy: String,
        @Query(value = "apiKey") apiKey: String,
    ): Response<News>

}