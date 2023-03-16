package com.example.fooding.data.source

import com.example.fooding.data.model.Post
import com.example.fooding.data.source.remote.ApiClient

class CalendarRepository(
    private val apiClient: ApiClient
) {
    suspend fun getPostByDate(orderBy: String, startAt: Long, endAt: Long): Map<String, Post> {
        return apiClient.getPost(orderBy, startAt, endAt)
    }
}