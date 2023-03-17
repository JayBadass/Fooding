package com.example.fooding.ui.addpost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.fooding.databinding.FragmentAddNutritionBinding

class AddNutritionFragment : Fragment() {

    private var _binding: FragmentAddNutritionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AddNutritionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddNutritionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addNutritionViewModel = viewModel
        setAddButton()
    }

    private fun setAddButton() {
        binding.btnAdd.setOnClickListener {
            viewModel.serializeNutrition()
            val data = viewModel.foodName.value
            val nutrition = viewModel.nutrition
            findNavController().previousBackStackEntry?.savedStateHandle?.set(
                "nutritionList",
                nutrition
            )
            findNavController().previousBackStackEntry?.savedStateHandle?.set("foodName", data)
            findNavController().popBackStack()
        }
    }

    private fun setSearchButton() {
        binding.btnSearch.setOnClickListener {
//            Search Fragment 이동
        }
    }
}