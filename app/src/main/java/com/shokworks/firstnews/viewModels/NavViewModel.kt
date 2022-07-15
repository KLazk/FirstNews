package com.shokworks.firstnews.viewModels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shokworks.firstnews.dbRoom.TFavNews
import com.shokworks.firstnews.network.entitys.Article
import com.shokworks.firstnews.network.entitys.News
import com.shokworks.firstnews.ui.NavigationActivity
import kotlinx.android.synthetic.main.activity_navigation.*

class NavViewModel: ViewModel() {

    /** Navegation de data ItemsNewFilter */
    var newList = MutableLiveData<News>()
    fun sendNavigationNewList(itemsNew: News) {
        newList.value = itemsNew
    }

    /** Navegation de data ItemFavNew */
    var new = MutableLiveData<Article>()
    fun sendNavigationNew(itemNew: Article) {
        new.value = itemNew
    }

    /** Navegation de data New */
    var article = MutableLiveData<Article>()
    fun sendNavArticle(itemNew: Article) {
        article.value = itemNew
    }
    /** Delete Navegation de data New */
    fun removeNavArgumt(activity: NavigationActivity) {
        article = MutableLiveData<Article>()
        favNews = MutableLiveData<TFavNews>()
    }

    /** Navegation de data FavNew */
    var favNews = MutableLiveData<TFavNews>()
    fun sendNavFavNew(itemFavNews: TFavNews) {
        favNews.value = itemFavNews
    }
}