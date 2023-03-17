package com.example.fooding.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.fooding.data.model.FoodResponse
import com.example.fooding.data.source.SearchRepository

class SearchViewModel(private val repository: SearchRepository) : ViewModel() {

    val foodName = MutableLiveData<String>()

    suspend fun getFoodList(): List<FoodResponse.Food>? {
        val foodName = foodName.value
        return repository.getFoodList(foodName!!)
    }

    companion object {
        fun provideFactory(repository: SearchRepository) = viewModelFactory {
            initializer {
                SearchViewModel(repository)
            }
        }
    }
}