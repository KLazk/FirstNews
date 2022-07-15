package com.shokworks.firstnews.ui.navSearch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.shokworks.firstnews.R
import com.shokworks.firstnews.databinding.FragmentUisearchnewsBinding
import com.shokworks.firstnews.network.entitys.Article
import com.shokworks.firstnews.providers.*
import com.shokworks.firstnews.ui.news.AdapterNews
import com.shokworks.firstnews.viewModels.MainViewModel
import com.shokworks.firstnews.viewModels.NavViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UISearchNews : Fragment() {

    private lateinit var _binding: FragmentUisearchnewsBinding
    private val binding get() = _binding

    /** Inject ViewModel */
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var navViewModel: NavViewModel
    @Inject
    lateinit var dialog: Dialog

    private val adapterNews: AdapterNews by lazy {
        AdapterNews(
            requireContext(),
            viewModel,
            dialog,
            arrayListOf()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentUisearchnewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        navViewModel = ViewModelProvider(requireActivity())[NavViewModel::class.java]

        /** Adapter RecyclerView */
        binding.rvNews.layoutManager = LinearLayoutManager(requireContext())
        binding.rvNews.adapter = adapterNews

        /** Evento Refresh de la ultima request */
        binding.idSwipeRefresh.setOnRefreshListener {
            binding.idSwipeRefresh.isRefreshing = true
            requestNews(isRefreshing = true)
        }

        /** Evento onClick con la lupa del teclado */
        binding.idSearchView.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (binding.idSearchView.length() != 0) {
                    viewModel.sendQueryNewList(binding.idSearchView.text.toString())
                    binding.idSearchView.setText(view.let { requireActivity().hideKeyboard(it) })
                    requestNews(isRefreshing = false)
                }
            }
            true
        }

        viewModel.score.observe(viewLifecycleOwner) { itemNew ->
            if (itemNew.isNotEmpty()){
                adapterNews.setNews(itemNew as ArrayList<Article>)
                binding.rvNews.onItemClick { _, p, _ ->
                    navViewModel.sendNavArticle(itemNew[p])
                    findNavController().navigate(R.id.action_nav_UISearchNews_to_nav_DetailsNews)
                }
            }
        }
    }

    /** Request de las Noticias recientes */
    private fun requestNews(
        isRefreshing: Boolean){
        binding.idInfoRv.visibility = View.GONE
        binding.rvNews.visibility = View.GONE
        binding.idShimmerNews.visibility = View.VISIBLE
        viewModel.vmGetNews(
            onSuccess = {
                try {
                    binding.idShimmerNews.visibility = View.GONE
                    if (isRefreshing) binding.idSwipeRefresh.isRefreshing = false
                    if (it?.articles!!.isNotEmpty()){
                        binding.rvNews.visibility = View.VISIBLE
                        binding.idInfoRv.visibility = View.GONE
                        viewModel.setArticle(itemsNew = it)
                        navViewModel.sendNavigationNewList(it)
                    } else {
                        binding.rvNews.visibility = View.GONE
                        binding.idInfoRv.visibility = View.VISIBLE
                        binding.idInfoRv.text = requireContext().getString(R.string.SinNoticias)
                    }
                } catch (e: Exception) {

                }
            },
            onFailure = { error, code ->
                binding.idShimmerNews.visibility = View.GONE
                if (isRefreshing) binding.idSwipeRefresh.isRefreshing = false
                NetworkResponseHttp.e(
                    context = requireContext(),
                    code = code,
                    mensaje = error,
                    codeHttp = { mensaje, descripcion ->
                        dialog.menssage(
                            context = requireContext(),
                            mensaje = mensaje,
                            descripcion = descripcion,
                            boolean = true,
                            refresh = {
                                if (it) {
                                    requestNews( isRefreshing = false)
                                }
                            }
                        )
                    }
                )
            }
        )
    }
}