package com.shokworks.firstnews.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shokworks.firstnews.network.entitys.Article
import timber.log.Timber

class NavViewModel: ViewModel() {

    /** Navegation de data New */
    var article = MutableLiveData<Article>()
    fun sendNavArticle(itemNew: Article) {
        article.value = itemNew
        Timber.e("MutableLiveData: ${article.value}")
    }
    /** Delete Navegation de data New */
    fun removeNavArticle() {
        article = MutableLiveData<Article>()
    }
}