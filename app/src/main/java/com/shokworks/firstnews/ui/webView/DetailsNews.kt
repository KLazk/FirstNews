package com.shokworks.firstnews.ui.webView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.webkit.WebViewCompat
import com.shokworks.firstnews.R
import com.shokworks.firstnews.databinding.FragmentDetailsnewsBinding
import com.shokworks.firstnews.dbRoom.TFavNews
import com.shokworks.firstnews.network.entitys.Article
import com.shokworks.firstnews.providers.ConstructObject
import com.shokworks.firstnews.providers.Dialog
import com.shokworks.firstnews.viewModels.MainViewModel
import com.shokworks.firstnews.viewModels.NavViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class DetailsNews : Fragment() {

    private lateinit var _binding: FragmentDetailsnewsBinding
    private val binding get() = _binding
    private var url: String? = null

    /** Inject ViewModel */
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var navViewModel: NavViewModel
    @Inject lateinit var dialog: Dialog
    @Inject lateinit var constructObject: ConstructObject

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetailsnewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        binding.idIconFav.setOnClickListener {
            findNavController().navigate(R.id.action_nav_UITabs_to_UISearchNews)
        }

        navViewModel = ViewModelProvider(requireActivity())[NavViewModel::class.java]
        /** Obtener la data de una Noticia Reciente */
        navViewModel.article.observe(viewLifecycleOwner) { item ->
            Timber.e("Accedio viewModelDetails Noticia Reciente: $item")
            url = item.url
            webView(isRefreshing = true, url = url!!)
            onClick(article = item, itemFavNew = null)
        }

        /** Obtener la data de una Noticia Favorita */
        navViewModel.favNews.observe(viewLifecycleOwner) { item ->
            Timber.e("Accedio viewModelDetails Noticia Favorita: $item")
            url = item.url
            webView(isRefreshing = true, url = url!!)
            onClick(article = null, itemFavNew = item)
        }

        binding.idSwipeRefresh.setOnRefreshListener {
            webView(isRefreshing = false, url = url!!)
            binding.idSwipeRefresh.isRefreshing = true
        }
    }

    private fun onClick(
        article: Article?,
        itemFavNew: TFavNews?
    ){
        if (article != null) {
            if (article.isFav) binding.idIconFav.setImageResource(R.drawable.ic_fav_true_24) else binding.idIconFav.setImageResource(R.drawable.ic_fav_false_24)
        } else binding.idIconFav.setImageResource(R.drawable.ic_fav_remove_24)
        /** onClick para abrir la ventana de confirmación para agregar o eliminar una noticia */
        binding.idIconFav.setOnClickListener {
            if (article != null) {
                if (article.isFav) binding.idIconFav.setImageResource(R.drawable.ic_fav_false_24) else binding.idIconFav.setImageResource(R.drawable.ic_fav_true_24)
                Timber.e("idIconFav desde noticias recientes isFav: ${!article.isFav}")
                viewModel.updateIsFav(itemNew = article, isFav = !article.isFav)
                navViewModel.sendNavigationNew(article)
            } else {
                binding.idIconFav.setImageResource(R.drawable.ic_fav_false_24)
                viewModel.vmDeleteFavNew(favNew = itemFavNew!!)
                navViewModel.sendNavigationNew(constructObject.addNews(itemFavNew))
            }
        }
    }
    /** Instancia del WebView */
    private fun webView(
        isRefreshing: Boolean,
        url: String){
        Timber.e("URL WebView: $url")
        if (isRefreshing) dialog.initProgress(requireContext())
        binding.idWebview.loadUrl(url)
        binding.idWebview.webViewClient = WebViewClient()
        val webViewPackageInfo = WebViewCompat.getCurrentWebViewPackage(requireContext())
        Timber.e("WebView version: ${webViewPackageInfo?.permissions}")
        binding.idWebview.webViewClient = object : WebViewClient() {
            override fun onPageFinished(
                view: WebView,
                url: String) {
                super.onPageFinished(view, url)
                if (!isRefreshing) binding.idSwipeRefresh.isRefreshing = false else dialog.finishProgress()
                binding.idInfoRv.visibility = View.GONE
                binding.idWebview.visibility = View.VISIBLE
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                Timber.e("onReceivedError: $error")
                if (!isRefreshing) binding.idSwipeRefresh.isRefreshing = false else dialog.finishProgress()
                binding.idInfoRv.visibility = View.VISIBLE
                binding.idWebview.visibility = View.GONE
                super.onReceivedError(view, request, error)
            }
        }
    }
}