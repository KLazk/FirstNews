package com.shokworks.firstnews.viewModels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shokworks.firstnews.dbRoom.TFavNews
import com.shokworks.firstnews.network.entitys.Article
import com.shokworks.firstnews.ui.NavigationActivity
import kotlinx.android.synthetic.main.activity_navigation.*
import timber.log.Timber

class NavViewModel: ViewModel() {

    /** Navegation de data New */
    var article = MutableLiveData<Article>()
    fun sendNavArticle(itemNew: Article) {
        article.value = itemNew
        Timber.e("MutableLiveData sendNavArticle: ${article.value}")
    }
    /** Delete Navegation de data New */
    fun removeNavArgumt(activity: NavigationActivity) {
        activity.idTitle.visibility = View.VISIBLE
        activity.idIconFav.visibility = View.GONE
        article = MutableLiveData<Article>()
        favNews = MutableLiveData<TFavNews>()
    }

    /** Navegation de data FavNew */
    var favNews = MutableLiveData<TFavNews>()
    fun sendNavFavNew(itemFavNews: TFavNews) {
        favNews.value = itemFavNews
        Timber.e("MutableLiveData sendNavFavNew: ${favNews.value}")
    }
}