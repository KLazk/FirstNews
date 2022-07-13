package com.shokworks.firstnews.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shokworks.firstnews.dbRoom.TFavNews
import com.shokworks.firstnews.network.Repository
import com.shokworks.firstnews.network.entitys.Article
import com.shokworks.firstnews.network.entitys.News
import com.shokworks.firstnews.providers.NetworkError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    /** Metodo observe de las noticias */
    fun getLiveDataNews(): LiveData<List<TFavNews>>? {
        return repository.dbListFavNews()
    }

    /** Metodo para guardar una noticia */
    fun vmInsertNew(
        favNew: Article
    ) {
        viewModelScope.launch {
            try {
                repository.dbInsertFavNew(favNew)
            } catch (e: Exception){
               // onFailure(e.message.toString(), 0)
            }
        }
    }

    /** Metodo para eliminar una noticia favorita */
    fun vmDeleteFavNew(
        favNew: TFavNews
    ) {
        viewModelScope.launch {
            try {
                repository.dbDeleteFavNew(favNew)
            } catch (e: Exception){
                // onFailure(e.message.toString(), 0)
            }
        }
    }

    /** Obtener en el VM las noticias mas recientes */
    fun vmGetNews(
        q: String,
        from: String,
        to: String,
        sortBy: String,
        apiKey: String,
        onSuccess: (News?) -> Unit,
        onFailure: (String, Int) -> Unit,
    ) {
        viewModelScope.launch {
            repository.apiGetNews(
                q = q,
                from = from,
                to = to,
                sortBy = sortBy,
                apiKey = apiKey,
                onSuccess = { response ->
                    this.launch {
                        try {
                            onSuccess(response)
                        } catch (e: Exception) {
                            if (e is CancellationException) {
                                throw e
                            }
                            onFailure(NetworkError.get(e.message), 0)
                        }
                    }
                },
                onFailure = { Error, code ->
                    onFailure(Error, code)
                }
            )
        }
    }
}