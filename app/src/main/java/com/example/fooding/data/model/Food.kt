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
        val NUTR_CONT8: String,
        val NUTR_CONT9: String,
        val NUTR_CONT4: String,
        val NUTR_CONT5: String,
        val NUTR_CONT6: String,
        val NUM: String,
        val NUTR_CONT7: String,
        val NUTR_CONT1: String,
        val NUTR_CONT2: String,
        val SUB_REF_NAME: String,
        val NUTR_CONT3: String,
        val RESEARCH_YEAR: String,
        val MAKER_NAME: String,
        val GROUP_NAME: String,
        val SERVING_SIZE: String,
        val SAMPLING_REGION_NAME: String,
        val SAMPLING_MONTH_CD: String,
        val SAMPLING_MONTH_NAME: String,
        val DESC_KOR: String,
        val SAMPLING_REGION_CD: String,
        val FOOD_CD: String,
    )
}