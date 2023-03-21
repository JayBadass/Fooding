package com.example.fooding.data.source

import com.example.fooding.data.model.Post
import com.example.fooding.data.source.remote.PostApiClient
import com.example.fooding.util.DateFormatText

class ChartRepository(private val apiClient: PostApiClient) {

    suspend fun getTodayPost(): Map<String, Post> {
        val todayLong = DateFormatText.getCurrentDateInMillis()
        return apiClient.getPostByTime("\"date\"", todayLong, todayLong)
    }

    suspend fun getRecentSevenDaysPost(): Map<String, Post> {
        val todayLong = DateFormatText.getCurrentDateInMillis()
        val sevenDaysAgoLong = DateFormatText.getSevenDaysAgoInMillis()
        return apiClient.getPostByTime("\"date\"", sevenDaysAgoLong, todayLong)
    }
}