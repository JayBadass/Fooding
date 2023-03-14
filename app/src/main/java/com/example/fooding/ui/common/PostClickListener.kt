package com.example.fooding.ui.common

import com.example.fooding.data.model.Post

interface PostClickListener {
    fun onPostClick(post: Post)
}