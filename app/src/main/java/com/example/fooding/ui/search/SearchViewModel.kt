package com.example.fooding.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fooding.data.model.FoodResponse
import com.example.fooding.data.source.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: SearchRepository) : ViewModel() {

    val foodName = MutableLiveData<String>()

    suspend fun getFoodList(): List<FoodResponse.Food> {
        val foodName = foodName.value
        return repository.getFoodList(foodName!!)
    }
}