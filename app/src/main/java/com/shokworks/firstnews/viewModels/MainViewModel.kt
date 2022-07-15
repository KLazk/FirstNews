package com.shokworks.firstnews.viewModels

import androidx.lifecycle.*
import com.shokworks.firstnews.dbRoom.TFavNews
import com.shokworks.firstnews.network.Repository
import com.shokworks.firstnews.network.entitys.Article
import com.shokworks.firstnews.network.entitys.News
import com.shokworks.firstnews.providers.NetworkError
import com.shokworks.firstnews.providers.timeCurrent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository) : ViewModel() {

    private var itemsNews: MutableLiveData<List<Article>> = MutableLiveData(listOf())
    val score: LiveData<List<Article>>
        get() = itemsNews

    /** Agregar Noticias a la lista */
    private var list = mutableListOf<Article>()
    fun setArticle(itemsNew: News?) {
        viewModelScope.launch {
            try {
                list = itemsNew?.articles as MutableList<Article>
                val listFavNews = repository.dbListAll() ?: listOf()
                if (listFavNews.isNotEmpty()){
                    list.forEach { itemNews ->
                        listFavNews.filter { ((itemNews.author == it.author) && (itemNews.title == it.title)) }.map { itemNews.isFav = true }
                    }
                }
                itemsNews.value = list
            } catch (e: Exception){
                e.message.toString()
            }
        }
    }

    /** Control para activar y desaptivar noticias de la lista general */
    fun updateIsFav(
        itemNew: Article,
        isFav: Boolean) {
        viewModelScope.launch {
            try {
                Timber.e("listas ViewModel: ${itemsNews.value!!.size}")
                when(isFav){
                    true -> { /** Add Noticia a la sesión de favoritos **/
                        repository.dbInsertFavNew(itemNew)
                        list.filter { ((it.author == itemNew.author) && (it.title == itemNew.title)) }.map { it.isFav = true }
                    }
                    false -> {/** Eliminar Noticia de la sesión de favoritos **/
                        repository.dbDeleteItemFav(itemNew)
                        list.filter { ((it.author == itemNew.author) && (it.title == itemNew.title)) }.map { it.isFav = false }
                    }
                }

                itemsNews.value = list
            } catch (e: Exception){
                e.message.toString()
            }
        }
    }

    fun updIsFav(
        itemNew: Article,
        isFav: Boolean) {
        viewModelScope.launch {
            try {
                Timber.e("listas ViewModel: ${itemsNews.value!!.size}")
                when(isFav){
                    true -> { /** Add Noticia a la sesión de favoritos **/
                        list.filter { ((it.author == itemNew.author) && (it.title == itemNew.title)) }.map { it.isFav = false }
                    }
                    false -> {/** Eliminar Noticia de la sesión de favoritos **/
                        list.filter { ((it.author == itemNew.author) && (it.title == itemNew.title)) }.map { it.isFav = true }
                    }
                }

                itemsNews.value = list
            } catch (e: Exception){
                e.message.toString()
            }
        }
    }

    /** Observe de las Noticias Favoritas */
    fun getLiveDataNews(): LiveData<List<TFavNews>>? {
        return repository.dbListFavNews()
    }

    /** Metodo para eliminar una noticia favorita */
    fun vmDeleteFavNew(favNew: TFavNews) {
        viewModelScope.launch {
            try {
                repository.dbDeleteFavID(favNew)
            } catch (e: Exception){
                e.message.toString()
            }
        }
    }

    /** Navegation de data ItemsNewFilter */
    var query = MutableLiveData<String>("apple")
    fun sendQueryNewList(tQuery: String) {
        query.value = tQuery
    }

    /** Obtener en el ViewModel las noticias más recientes */
    fun vmGetNews(
        onSuccess: (News?) -> Unit,
        onFailure: (String, Int) -> Unit,
    ) {
        viewModelScope.launch {
            val timer = timeCurrent()
            repository.apiGetNews(
                q = query.value.toString(),
                from = timer,
                to = timer,
                sortBy = "popularity",
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