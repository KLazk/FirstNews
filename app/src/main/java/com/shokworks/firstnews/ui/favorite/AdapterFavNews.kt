package com.shokworks.firstnews.ui.favorite

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.shokworks.firstnews.R
import com.shokworks.firstnews.databinding.ItemNewsBinding
import com.shokworks.firstnews.dbRoom.TFavNews
import com.shokworks.firstnews.providers.*
import com.shokworks.firstnews.viewModels.MainViewModel
import com.shokworks.firstnews.viewModels.NavViewModel

class AdapterFavNews(
    private val context: Context,
    private var viewModel: MainViewModel,
    private var navViewModel: NavViewModel,
    private var constructObject: ConstructObject,
    private var dialog: Dialog,
    private var listNews: ArrayList<TFavNews>
) : RecyclerView.Adapter<AdapterFavNews.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemNewsBinding.inflate(LayoutInflater.from(context) , parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listNews[position]
        val timeFormat = stringDateToTimeFormat(fecha = converterUTC(item.publishedAt))
        holder.viewBinding.idDate.text = String.format("%s %s %s", getWeekNameShort(timeFormat.dayOfWeek), timeFormat.day.toString(), getNameMonthShort(timeFormat.month))
        holder.viewBinding.idtitle.text = item.title
        holder.viewBinding.idIconFav.setImageResource(R.drawable.ic_fav_remove_24)
        holder.viewBinding.idIconFav.setOnClickListener {
            /** onClick para abrir la ventana de confirmación para eliminar de la sesion de favoritos una noticia */
            dialog.bottomSheet(
                context = context,
                mensaje = item.title,
                descripcion = context.getString(R.string.confirmarEliminar),
                boolean = false,
                refresh = {
                    if (it) {
                        viewModel.vmDeleteFavNew(favNew = item)
                        navViewModel.sendNavigationNew(constructObject.addNews(item))
                    }
                }
            )
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
    internal fun setNews(list: ArrayList<TFavNews>, navViewModel: NavViewModel) {
        this.navViewModel = navViewModel
        listNews = list
        notifyDataSetChanged()
    }
}