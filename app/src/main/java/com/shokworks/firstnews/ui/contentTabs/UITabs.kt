package com.shokworks.firstnews.ui.contentTabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.shokworks.firstnews.R
import com.shokworks.firstnews.databinding.FragmentUitabsBinding
import com.shokworks.firstnews.provides.AdapterTabPager
import com.shokworks.firstnews.ui.favorite.UIFavorite
import com.shokworks.firstnews.ui.news.UINew
import timber.log.Timber

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
        Timber.e("UITabs")
        val adapter = AdapterTabPager(activity)
        adapter.addFragment(UINew(), requireContext().getString(R.string.Recientes))
        adapter.addFragment(UIFavorite(),  requireContext().getString(R.string.Favoritas))

        binding.viewPager.adapter = adapter
        binding.viewPager.currentItem = 0
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = adapter.getTabTitle(position)
        }.attach()

    }



}