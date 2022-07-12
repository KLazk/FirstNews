package com.shokworks.firstnews.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shokworks.firstnews.databinding.FragmentUifavoriteBinding

class UIFavorite : Fragment() {

    private lateinit var _binding: FragmentUifavoriteBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentUifavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

}