package com.shokworks.firstnews.ui.contentTabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.shokworks.firstnews.R
import com.shokworks.firstnews.databinding.FragmentUitabsBinding
import com.shokworks.firstnews.providers.AdapterTabPager
import com.shokworks.firstnews.ui.NavigationActivity
import com.shokworks.firstnews.ui.favorite.UIFavorite
import com.shokworks.firstnews.ui.news.UINew
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_navigation.*

@AndroidEntryPoint
class UITabs : Fragment() {

    private lateinit var _binding: FragmentUitabsBinding
    private val binding get() = _binding

    private lateinit var activity: NavigationActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentUitabsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (getActivity() as NavigationActivity).also { activity = it }
        activity.idIconFav.setOnClickListener {
            activity.idIconFav.visibility = View.GONE
            activity.idTitle.visibility = View.GONE
            findNavController().navigate(R.id.action_nav_UITabs_to_UISearchNews)
        }

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