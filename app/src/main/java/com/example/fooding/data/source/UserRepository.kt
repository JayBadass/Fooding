package com.example.fooding.data.source

import com.example.fooding.data.model.Post
import com.example.fooding.data.model.User
import com.example.fooding.data.source.local.UserDao
import com.example.fooding.data.source.remote.PostApiClient
import com.example.fooding.util.DateFormatText
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao,
    private val apiClient: PostApiClient
) {

    suspend fun getUserById(id: String): User {
        return userDao.getUser(id)
    }

    suspend fun saveUser(user: User) {
        return userDao.insertUser(user)
    }

    suspend fun getPostCount(): Int {
        val apiClient = PostApiClient.create()
        val posts = apiClient.getAllPost()
        return posts.size
    }

    suspend fun getTodayPost(): Map<String, Post> {
        val todayLong = DateFormatText.getCurrentDateInMillis()
        return apiClient.getPostByTime("\"date\"", todayLong, todayLong)
    }

    suspend fun updateUser(user: User) {
        userDao.updateWeightCalories(user)
    }
}