package com.shokworks.firstnews.ui.news

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.shokworks.firstnews.R
import com.shokworks.firstnews.databinding.ItemNewsBinding
import com.shokworks.firstnews.network.entitys.Article
import com.shokworks.firstnews.providers.*
import com.shokworks.firstnews.viewModels.MainViewModel

class AdapterNews(
    private val context: Context,
    private var viewModel: MainViewModel,
    private var dialog: Dialog,
    private var listNews: ArrayList<Article>
) : RecyclerView.Adapter<AdapterNews.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemNewsBinding.inflate(LayoutInflater.from(context) , parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listNews[position]
        val timeFormat = stringDateToTimeFormat(fecha = converterUTC(item.publishedAt))
        holder.viewBinding.idDate.text = String.format("%s %s %s", getWeekNameShort(timeFormat.dayOfWeek), timeFormat.day.toString(), getNameMonthShort(timeFormat.month))
        holder.viewBinding.idtitle.text = item.title
        holder.viewBinding.idDescription.text = item.description

        when(item.isFav){
            true -> holder.viewBinding.idIconFav.setImageResource(R.drawable.ic_fav_true_24)
            false -> holder.viewBinding.idIconFav.setImageResource(R.drawable.ic_fav_false_24)
        }

        holder.viewBinding.idIconFav.setOnClickListener {
            /** onClick para abrir la ventana de confirmación para agregar a favorito una noticia */
            when(item.isFav){
                true -> viewModel.updateIsFav(itemNew = item, isFav = false)
                false -> viewModel.updateIsFav(itemNew = item, isFav = true)
            }
        }

        GlideApp.with(context)
            .load(item.urlToImage)
            .placeholder(dialog.shimmerEffect())
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.viewBinding.idImgNews)

    }

    override fun getItemCount(): Int { return listNews.size }

    inner class ViewHolder(var viewBinding: ItemNewsBinding) : RecyclerView.ViewHolder(viewBinding.root)

    @SuppressLint("NotifyDataSetChanged")
    internal fun setNews(list: ArrayList<Article>) {
        listNews = list
        notifyDataSetChanged()
    }
}