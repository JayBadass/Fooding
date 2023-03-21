package com.example.fooding.data.source

import com.example.fooding.data.model.Post
import com.example.fooding.data.model.User
import com.example.fooding.data.source.local.AppDatabase
import com.example.fooding.data.source.remote.PostApiClient
import com.example.fooding.util.DateFormatText
import java.text.SimpleDateFormat
import java.util.*

class UserRepository(database: AppDatabase, private val apiClient: PostApiClient) {

    private val dao = database.userDao()

    suspend fun getUserById(id: String): User {
        return dao.getUser(id)
    }

    suspend fun saveUser(user: User) {
        return dao.insertUser(user)
    }

    suspend fun getPostCount(): Int {
        val apiClient = PostApiClient.create()
        val posts = apiClient.getAllPost()
        return posts.size
    }

    suspend fun getTodayPost(): Map<String, Post> {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val today = DateFormatText.getCurrentDate()
        val todayLong = dateFormat.parse(today)!!.time
        return apiClient.getPostByTime("\"date\"", todayLong, todayLong)
    }

    suspend fun updateUser(user: User) {
        dao.updateWeightCalories(user)
    }
}