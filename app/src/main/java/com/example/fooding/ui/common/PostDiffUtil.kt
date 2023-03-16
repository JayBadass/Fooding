package com.example.fooding.ui.common

import androidx.recyclerview.widget.DiffUtil
import com.example.fooding.data.model.Post

class PostDiffUtil: DiffUtil.ItemCallback<Post>() {

    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}