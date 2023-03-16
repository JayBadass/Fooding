package com.example.fooding.ui.calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.example.fooding.data.model.Post
import com.example.fooding.databinding.ItemPostBinding
import com.example.fooding.ui.common.PostClickListener
import com.example.fooding.ui.common.PostDiffUtil

class PostListAdapter(private val clickListener: PostClickListener) :
    ListAdapter<Post, PostListAdapter.PostListViewHolder>(PostDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostListViewHolder {
        return PostListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PostListViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class PostListViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Post, clickListener: PostClickListener) {
            binding.clickListener = clickListener
            binding.post = item
            Glide.with(itemView.context)
                .load(item.imgUri)
                .into(binding.imageFood)
        }

        companion object {
            fun from(parent: ViewGroup): PostListViewHolder {
                val binding = ItemPostBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return PostListViewHolder(binding)
            }
        }
    }
}