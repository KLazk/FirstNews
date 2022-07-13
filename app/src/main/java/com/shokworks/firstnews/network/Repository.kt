package com.shokworks.firstnews.network

import androidx.compose.ui.platform.AndroidUiDispatcher.Companion.Main
import com.shokworks.firstnews.network.entitys.News
import com.shokworks.firstnews.providers.NetworkError
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class Repository @Inject constructor(private val apiServices: ApiServices) {

    /** Metodo para obtener las noticias */
    suspend fun apiGetNews(
        q: String,
        from: String,
        to: String,
        sortBy: String,
        apiKey: String,
        onSuccess: (News?) -> Unit,
        onFailure: (String, Int) -> Unit,
    ) {
        val response: Response<News>
        var code = 0
        try {
            response = apiServices.getNews(q = q, from = from, to = to, sortBy = sortBy, apiKey = apiKey)
            code = response.code()
            when {
                response.isSuccessful -> {
                    withContext(Main) {
                        Timber.e("Code Response: ${response.code()}")
                        if (response.body() != null) onSuccess(response.body()) else onSuccess(null)
                    }
                }
                else -> {
                    withContext(Main) {
                        onFailure(NetworkError.get(response.message()), code)
                    }
                }
            }
        } catch (e: Exception) {
            if (e is CancellationException) {
                throw e
            }
            withContext(Dispatchers.Main) {
                onFailure(NetworkError.get(e.message), code)
            }
        }
    }

}