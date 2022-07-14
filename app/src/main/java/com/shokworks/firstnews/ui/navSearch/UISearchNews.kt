package com.shokworks.firstnews.ui.navSearch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.shokworks.firstnews.databinding.FragmentUisearchnewsBinding
import com.shokworks.firstnews.ui.NavigationActivity
import kotlinx.android.synthetic.main.activity_navigation.*

class UISearchNews : Fragment() {

    private lateinit var _binding: FragmentUisearchnewsBinding
    private val binding get() = _binding
    private lateinit var activity: NavigationActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentUisearchnewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (getActivity() as NavigationActivity).also { activity = it }
        activity.toolbar.setNavigationOnClickListener { popBackStack() }

    }

    /** onBackPressed */
    private fun popBackStack() {
        activity.idIconFav.visibility = View.VISIBLE
        activity.idTitle.visibility = View.VISIBLE
        findNavController().popBackStack()
    }

}