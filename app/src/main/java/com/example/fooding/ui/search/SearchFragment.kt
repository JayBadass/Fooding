package com.example.fooding.ui.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooding.FoodingApplication
import com.example.fooding.data.model.Nutrition
import com.example.fooding.data.source.SearchRepository
import com.example.fooding.databinding.FragmentSearchBinding
import com.example.fooding.ui.common.FoodClickListener
import kotlinx.coroutines.launch

class SearchFragment : Fragment(), FoodClickListener {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModels {
        SearchViewModel.provideFactory(
            repository = SearchRepository(FoodingApplication.appContainer.provideFoodApiClient())
        )
    }

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
                Log.d("fragment", "$foodList")
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

    override fun onClick(nutrition: Nutrition) {
        TODO("Not yet implemented")
    }
}