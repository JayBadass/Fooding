package com.example.fooding.data.source

import com.example.fooding.BuildConfig
import com.example.fooding.data.model.FoodResponse
import com.example.fooding.data.source.remote.FoodApiClient
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val apiClient: FoodApiClient
) {
    suspend fun getFoodList(foodName: String): List<FoodResponse.Food> {
        return apiClient.getFoodList(BuildConfig.SERVICE_KEY, foodName).foodInfo.row
    }
}