package com.shokworks.firstnews.ui.news

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shokworks.firstnews.databinding.ItemNewsBinding
import com.shokworks.firstnews.network.entitys.Article

class AdapterNews(
private val context: Context,
private var listNews: ArrayList<Article>
) : RecyclerView.Adapter<AdapterNews.CarteleraViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarteleraViewHolder {
        val view = ItemNewsBinding.inflate(LayoutInflater.from(context) , parent,false)
        return CarteleraViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarteleraViewHolder, position: Int) {
        val item = listNews[position]
        holder.viewBinding.idtitle.text = item.title
    }

    override fun getItemCount(): Int { return listNews.size }

    inner class CarteleraViewHolder(var viewBinding: ItemNewsBinding) : RecyclerView.ViewHolder(viewBinding.root)

    internal fun setNews(list: ArrayList<Article>) {
        listNews = list
        notifyDataSetChanged()
    }
}