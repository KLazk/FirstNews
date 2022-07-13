package com.shokworks.firstnews.ui.webView

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModelProvider
import com.shokworks.firstnews.databinding.FragmentDetailsnewsBinding
import com.shokworks.firstnews.viewModels.NavViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class DetailsNews : Fragment() {

    private lateinit var _binding: FragmentDetailsnewsBinding
    private val binding get() = _binding

    private lateinit var navViewModel: NavViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetailsnewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navViewModel = ViewModelProvider(requireActivity())[NavViewModel::class.java]
        /** Obtener la data del cliente seleccionado */
        navViewModel.article.observe(viewLifecycleOwner) { item ->
            Timber.e("Accedio por viewModelDetails: $item")
            webView(item.url)
        }
    }

    private fun webView(url: String){
        binding.idWebview.loadUrl(url)
        binding.idWebview.webViewClient = WebViewClient()
    }
}