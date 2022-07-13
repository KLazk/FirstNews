package com.shokworks.firstnews.ui.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.shokworks.firstnews.R
import com.shokworks.firstnews.databinding.FragmentUinewBinding
import com.shokworks.firstnews.network.entitys.Article
import com.shokworks.firstnews.providers.onItemClick
import com.shokworks.firstnews.viewModels.MainViewModel
import com.shokworks.firstnews.viewModels.NavViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class UINew : Fragment() {

    private lateinit var _binding: FragmentUinewBinding
    private val binding get() = _binding

    /** Inject ViewModel */
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var navViewModel: NavViewModel

    private val adapterNews: AdapterNews by lazy {
        AdapterNews(
            requireContext(),
            arrayListOf()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentUinewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navViewModel = ViewModelProvider(requireActivity())[NavViewModel::class.java]

        /** Adapter RecyclerView */
        binding.rvNews.layoutManager = LinearLayoutManager(requireContext())
        binding.rvNews.adapter = adapterNews

        viewModel.vmGetNews(
            q = "apple",
            from = "2022-07-11",
            to = "2022-07-11",
            sortBy = "popularity",
            apiKey = "c19b337c88ba447082d977a58a4f33dd",
            onSuccess = {
                Timber.e("News: $it")
                adapterNews.setNews(it?.articles as ArrayList<Article>)

                binding.rvNews.onItemClick { _, p, _ ->
                    navViewModel.sendNavArticle(it.articles[p])
                    findNavController().navigate(R.id.action_nav_UITabs_to_nav_DetailsNews)
                }
            },
            onFailure = { _, _ ->

            }
        )
    }
}