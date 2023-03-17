package com.example.fooding.ui.common

import androidx.recyclerview.widget.DiffUtil
import com.example.fooding.data.model.FoodResponse


class FoodDiffUtil : DiffUtil.ItemCallback<FoodResponse.Food>() {

    override fun areItemsTheSame(oldItem: FoodResponse.Food, newItem: FoodResponse.Food): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: FoodResponse.Food, newItem: FoodResponse.Food): Boolean {
        return oldItem == newItem
    }
}