package com.example.fooding.ui.mypage

import androidx.lifecycle.ViewModel
import com.example.fooding.data.model.User
import com.example.fooding.data.source.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    suspend fun loadUserData(id: String): User {
        return userRepository.getUserById(id)
    }

    suspend fun getPostCount(): String {
        return userRepository.getPostCount().toString()
    }

    suspend fun getIntakeCalories(): String {
        val posts = userRepository.getTodayPost()
        var totalCalories = 0.00
        for (post in posts.values) {
            totalCalories += post.nutritionInfo?.NUTR_CONT1?.toDouble() ?: 0.00
        }
        return totalCalories.toString()
    }

    suspend fun updateUserData(id: String, updateFields: Map<String, String>) {
        val user = userRepository.getUserById(id)
        val updatedUser = user.copy(
            currentWeight = updateFields["currentWeight"] ?: user.currentWeight,
            goalWeight = updateFields["goalWeight"] ?: user.goalWeight,
            goalCalories = updateFields["goalCalories"] ?: user.goalCalories
        )
        userRepository.updateUser(updatedUser)
    }
}