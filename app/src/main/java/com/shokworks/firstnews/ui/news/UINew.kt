package com.shokworks.firstnews.ui.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shokworks.firstnews.databinding.FragmentUinewBinding

class UINew : Fragment() {

    private lateinit var _binding: FragmentUinewBinding
    private val binding get() = _binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentUinewBinding.inflate(inflater, container, false)
        return binding.root
    }
}