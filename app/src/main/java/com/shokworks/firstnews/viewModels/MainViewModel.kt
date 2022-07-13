package com.shokworks.firstnews.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shokworks.firstnews.network.Repository
import com.shokworks.firstnews.network.entitys.Article
import com.shokworks.firstnews.network.entitys.News
import com.shokworks.firstnews.providers.NetworkError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    /** Obtener en el VM las noticias mas recientes l*/
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