package com.example.fooding.data.model

import android.net.Uri
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Post(
    val time: String,
    val category: String?,
    val memo: String?,
    val imgUri: Uri?,
    val nutritionInfo: Nutrition?,
) : java.io.Serializable

@JsonClass(generateAdapter = true)
data class Nutrition(
    val foodName: String?,
    val servingSize: String?,
    val calories: String?,
    val carb: String?,
    val protein: String?,
    val fat: String?,
    val sugar: String?,
    val sodium: String?,
    val cholesterol: String?,
    val saturatedFat: String?,
    val transFat: String?,
) : java.io.Serializable