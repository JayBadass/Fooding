package com.example.fooding.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooding.HomeGraphDirections
import com.example.fooding.data.model.FoodResponse
import com.example.fooding.databinding.FragmentSearchBinding
import com.example.fooding.ui.common.FoodClickListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(), FoodClickListener {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchViewModel = viewModel
        setLayout()
        setSearchButton()
    }

    private fun setLayout() {
        val adapter = FoodListAdapter(this)
        binding.rvFoodList.adapter = adapter
        binding.rvFoodList.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setSearchButton() {
        binding.btnSearch.setOnClickListener {
            lifecycleScope.launch {
                showLoadingIndicator()
                val foodList = viewModel.getFoodList()
                if (foodList != null) {
                    (binding.rvFoodList.adapter as FoodListAdapter).submitList(foodList)
                }
                hideLoadingIndicator()
            }
        }
    }

    private fun showLoadingIndicator() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoadingIndicator() {
        binding.progressBar.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(food: FoodResponse.Food) {
        val action = HomeGraphDirections.actionGlobalSearchDetail(food)
        findNavController().navigate(action)
    }
}