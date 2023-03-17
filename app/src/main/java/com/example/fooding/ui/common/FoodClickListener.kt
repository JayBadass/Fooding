package com.example.fooding.ui.common

import com.example.fooding.data.model.Nutrition

interface FoodClickListener {
    fun onClick(nutrition: Nutrition)
}