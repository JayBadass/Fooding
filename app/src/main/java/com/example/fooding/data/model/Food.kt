package com.example.fooding.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FoodResponse(
    @Json(name = "I2790") val foodInfo: FoodInfo,
) {
    @JsonClass(generateAdapter = true)
    data class FoodInfo(
        val total_count: String,
        val row: List<Food>,
        val RESULT: Result,
    ) {
        @JsonClass(generateAdapter = true)
        data class Result(
            val MSG: String,
            val CODE: String,
        )
    }

    @JsonClass(generateAdapter = true)
    data class Food(
        val DESC_KOR: String?,
        val SERVING_SIZE: String?,
        val NUTR_CONT1: String?,
        val NUTR_CONT2: String?,
        val NUTR_CONT3: String?,
        val NUTR_CONT4: String?,
        val NUTR_CONT5: String?,
        val NUTR_CONT6: String?,
        val NUTR_CONT7: String?,
        val NUTR_CONT8: String?,
        val NUTR_CONT9: String?,
        val SUB_REF_NAME: String?,
        val NUM: String?,
        val RESEARCH_YEAR: String?,
        val MAKER_NAME: String?,
        val GROUP_NAME: String?,
        val SAMPLING_REGION_NAME: String?,
        val SAMPLING_MONTH_CD: String?,
        val SAMPLING_MONTH_NAME: String?,
        val SAMPLING_REGION_CD: String?,
        val FOOD_CD: String?,
    ) : java.io.Serializable
}

data class Nutrition(
    val servingSize: String,
    val calories: String,
    val carb: String,
    val protein: String,
    val fat: String,
    val sugars: String,
    val sodium: String,
    val cholesterol: String,
    val saturatedFat: String,
    val transFat: String,
)