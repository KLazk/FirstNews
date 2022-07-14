package com.shokworks.firstnews.network

import androidx.compose.ui.platform.AndroidUiDispatcher.Companion.Main
import androidx.lifecycle.LiveData
import com.shokworks.firstnews.App
import com.shokworks.firstnews.BuildConfig
import com.shokworks.firstnews.dbRoom.TFavNews
import com.shokworks.firstnews.network.entitys.Article
import com.shokworks.firstnews.network.entitys.News
import com.shokworks.firstnews.providers.ConstructObject
import com.shokworks.firstnews.providers.NetworkError
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(private val apiServices: ApiServices) {

    @Inject lateinit var constructObject: ConstructObject

    /** ---------------------- METODO DB ROOM PARA LAS NOTICIAS FAVORITAS ----------------------- */

    /** Instancie del Query de la DB para insert una Noticia Favorita */
    suspend fun dbInsertFavNew(article: Article) = withContext(Dispatchers.IO){
        return@withContext App.database.scDao().insertFavNoticie(constructObject.addTFavNews(article))
    }

    /** Instancie del Query de la DB para obtener la lista de Noticias Favoritas */
    suspend fun dbListAll() = withContext(Dispatchers.IO){
        return@withContext App.database.scDao().getListAll()
    }

    /** Instancie del Query de la DB para observe Noticias Favoritas */
    fun dbListFavNews(): LiveData<List<TFavNews>>?{
        return App.database.scDao().getListFavNoticie()
    }

    /** Instancie del Query de la DB para eliminar una Noticia Favorita por id */
    suspend fun dbDeleteFavID(itemFav: TFavNews) = withContext(Dispatchers.IO){
        return@withContext App.database.scDao().deleteItemFavNew(itemFav.id)
    }

    /** Instancie del Query de la DB para eliminar una Noticia Favorita desde las noticias recientes*/
    suspend fun dbDeleteItemFav(itemFavNews: Article) = withContext(Dispatchers.IO){
        return@withContext App.database.scDao().deleteItemFav(itemFavNews.author, itemFavNews.title)
    }

    /** Instancie de la DB para eliminar todas las Noticies Favorites */
    suspend fun dbDeleteFavNewsAll() = withContext(Dispatchers.IO){
        return@withContext App.database.scDao().deleteFavNoticiesAll()
    }

     /** ---------------------- METODO REQUEST DE LA API ----------------------------- */

    /** Metodo para obtener las noticias */
    suspend fun apiGetNews(
        q: String,
        from: String,
        to: String,
        sortBy: String,
        onSuccess: (News?) -> Unit,
        onFailure: (String, Int) -> Unit,
    ) {
        val response: Response<News>
        var code = 0
        try {
            response = apiServices.getNews(
                q = q,
                from = from,
                to = to,
                sortBy = sortBy,
                apiKey = BuildConfig.NEWS_ID)
            code = response.code()
            when {
                response.isSuccessful -> {
                    withContext(Main) {
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