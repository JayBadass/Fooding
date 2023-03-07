package com.example.fooding.data.model

import android.net.Uri
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Post(
    val time: String,
    val category: String?,
    val memo: String?,
    val imgUri: Uri?,
    val nutrition: List<Nutrition>?,
)

@JsonClass(generateAdapter = true)
data class Nutrition(
    val name: String,
    val servingWt: Int,
    val calories: Int,
    val carb: Int,
    val protein: Int,
    val fat: Int,
    val sugar: Int,
    val sodium: Int,
    val cholesterol: Int,
    val saturatedFat: Int,
    val transFat: Int,
)