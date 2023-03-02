package com.example.fooding.ui.addfood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fooding.databinding.FragmentAddfoodBinding

class AddFoodFragment : Fragment() {

    private var _binding: FragmentAddfoodBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddfoodBinding.inflate(inflater, container, false)
        return binding.root
    }
}