package com.example.fooding.data.source

import com.example.fooding.data.model.Post
import com.example.fooding.data.source.remote.PostApiClient

class CalendarRepository(
    private val postApiClient: PostApiClient
) {
    suspend fun getPostByDate(orderBy: String, startAt: Long, endAt: Long): Map<String, Post> {
        return postApiClient.getPostByTime(orderBy, startAt, endAt)
    }
}