package com.example.fooding.data.model

import android.net.Uri
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Post(
    val foodName: String?,
    val date: Long?,
    val time: String?,
    val category: String?,
    val memo: String?,
    val imgUri: String?,
    val nutritionInfo: FoodResponse.Food?,
) : java.io.Serializable
