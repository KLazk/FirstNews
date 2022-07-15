package com.shokworks.firstnews.ui.contentTabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.shokworks.firstnews.R
import com.shokworks.firstnews.databinding.FragmentUitabsBinding
import com.shokworks.firstnews.providers.AdapterTabPager
import com.shokworks.firstnews.ui.favorite.UIFavorite
import com.shokworks.firstnews.ui.news.UINew
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UITabs : Fragment() {

    private lateinit var _binding: FragmentUitabsBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentUitabsBinding.inflate(inflater, container, false)
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

        val adapter = AdapterTabPager(activity)
        adapter.addFragment(UINew(), requireContext().getString(R.string.Recientes))
        adapter.addFragment(UIFavorite(),  requireContext().getString(R.string.Favoritas))
        binding.viewPager.isUserInputEnabled = true
        binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.viewPager.adapter = adapter
        binding.viewPager.currentItem = 0
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = adapter.getTabTitle(position)
        }.attach()
    }
}