package com.example.fooding.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fooding.data.model.FoodResponse
import com.example.fooding.databinding.ItemFoodBinding
import com.example.fooding.ui.common.FoodClickListener
import com.example.fooding.ui.common.FoodDiffUtil

class FoodListAdapter(private val clickListener: FoodClickListener) :
    ListAdapter<FoodResponse.Food, FoodListAdapter.FoodListViewHolder>(FoodDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodListViewHolder {
        return FoodListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FoodListViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class FoodListViewHolder(private val binding: ItemFoodBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FoodResponse.Food, clickListener: FoodClickListener) {
            binding.food = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): FoodListViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                return FoodListViewHolder(
                    ItemFoodBinding.inflate(inflater, parent, false),
                )
            }
        }
    }
}