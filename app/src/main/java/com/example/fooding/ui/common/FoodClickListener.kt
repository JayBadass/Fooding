package com.example.fooding.ui.common

import com.example.fooding.data.model.FoodResponse

interface FoodClickListener {
    fun onClick(food: FoodResponse.Food)
}