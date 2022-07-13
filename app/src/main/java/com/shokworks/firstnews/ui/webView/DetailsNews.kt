package com.shokworks.firstnews.ui.webView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.shokworks.firstnews.R
import com.shokworks.firstnews.databinding.FragmentDetailsnewsBinding
import com.shokworks.firstnews.dbRoom.TFavNews
import com.shokworks.firstnews.network.entitys.Article
import com.shokworks.firstnews.providers.Dialog
import com.shokworks.firstnews.ui.NavigationActivity
import com.shokworks.firstnews.viewModels.MainViewModel
import com.shokworks.firstnews.viewModels.NavViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_navigation.*
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class DetailsNews : Fragment() {

    private lateinit var _binding: FragmentDetailsnewsBinding
    private val binding get() = _binding
    private lateinit var activity: NavigationActivity
    private lateinit var navViewModel: NavViewModel

    /** Inject ViewModel */
    private val viewModel by viewModels<MainViewModel>()
    @Inject lateinit var dialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetailsnewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (getActivity() as NavigationActivity).also { activity = it }
        activity.idTitle.visibility = View.GONE

        navViewModel = ViewModelProvider(requireActivity())[NavViewModel::class.java]
        /** Obtener la data de una Noticia Reciente */
        navViewModel.article.observe(viewLifecycleOwner) { item ->
            Timber.e("Accedio viewModelDetails Noticia Reciente: $item")
            webView(item.url)
            onClick(boolean = true, article = item, itemFavNew = null)
        }

        /** Obtener la data de una Noticia Favorita */
        navViewModel.favNews.observe(viewLifecycleOwner) { item ->
            Timber.e("Accedio viewModelDetails Noticia Favorita: $item")
            webView(item.url)
            onClick(boolean = false, article = null, itemFavNew = item)
        }
        /** onBackPressed */
        activity.toolbar.setNavigationOnClickListener { popBackStack() }
    }

    private fun onClick(
        boolean: Boolean,
        article: Article?,
        itemFavNew: TFavNews?
    ){
        /** onClick para abrir la ventana de confirmación para agregar o eliminar una noticia */
        activity.idIconFav.setOnClickListener {
            dialog.bottomSheet(
                context = requireContext(),
                mensaje = "",
                descripcion = requireContext().getString(R.string.confirmarEliminar),
                boolean = boolean,
                refresh = {
                    if (it) {
                        if (article != null) viewModel.vmInsertNew(favNew = article) else viewModel.vmDeleteFavNew(favNew = itemFavNew!!)
                        popBackStack()
                    }
                }
            )
        }
    }
    /** Instancia del WebView */
    private fun webView(url: String){
        binding.idWebview.loadUrl(url)
        binding.idWebview.webViewClient = WebViewClient()
        binding.idWebview.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
                Timber.e("webViewClient cargo exitosamente...")
            }
        }
    }
    /** onBackPressed */
    private fun popBackStack() {
        navViewModel.removeNavArgumt(activity)
        findNavController().popBackStack()
    }
}