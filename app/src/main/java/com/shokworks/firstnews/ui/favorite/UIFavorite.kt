package com.shokworks.firstnews.ui.favorite

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
import com.shokworks.firstnews.databinding.FragmentUifavoriteBinding
import com.shokworks.firstnews.dbRoom.TFavNews
import com.shokworks.firstnews.providers.ConstructObject
import com.shokworks.firstnews.providers.Dialog
import com.shokworks.firstnews.providers.onItemClick
import com.shokworks.firstnews.viewModels.MainViewModel
import com.shokworks.firstnews.viewModels.NavViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UIFavorite : Fragment() {

    private lateinit var _binding: FragmentUifavoriteBinding
    private val binding get() = _binding

    /** Inject ViewModel */
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var navViewModel: NavViewModel
    @Inject lateinit var sheetBottom: Dialog
    @Inject lateinit var constructObject: ConstructObject

    private val adapterFavNews: AdapterFavNews by lazy {
        AdapterFavNews(
            requireContext(),
            viewModel,
            navViewModel,
            constructObject,
            sheetBottom,
            arrayListOf()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentUifavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navViewModel = ViewModelProvider(requireActivity())[NavViewModel::class.java]

        /** Adapter RecyclerView */
        binding.rvFavNews.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFavNews.adapter = adapterFavNews
        viewModel.getLiveDataNews()?.observe(viewLifecycleOwner) { response ->
            if (response.isNotEmpty()) {
                binding.rvFavNews.visibility = View.VISIBLE
                adapterFavNews.setNews(response as ArrayList<TFavNews>, navViewModel)
                binding.rvFavNews.onItemClick { _, p, _ ->
                    navViewModel.sendNavFavNew(response[p])
                    findNavController().navigate(R.id.action_nav_UITabs_to_nav_DetailsNews)
                }
            } else {
                binding.rvFavNews.visibility = View.GONE
            }
        }
    }
}