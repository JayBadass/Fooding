package com.example.fooding.ui.chart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.fooding.data.model.Nutrition
import com.example.fooding.data.source.ChartRepository
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class ChartViewModel(private val repository: ChartRepository) : ViewModel() {

    suspend fun getTodayNutrition(): Nutrition {
        val posts = repository.getTodayPost()
        var servingSizeLong = 0.00
        var caloriesLong = 0.00
        var carbLong = 0.00
        var proteinLong = 0.00
        var fatLong = 0.00
        var sugarsLong = 0.00
        var sodiumLong = 0.00
        var cholesterolLong = 0.00
        var saturatedFatLong = 0.00
        var transFatLong = 0.00
        for (post in posts.values) {
            servingSizeLong += post.nutritionInfo?.SERVING_SIZE?.takeIf { it.isNotBlank() }
                ?.toDoubleOrNull() ?: 0.0
            caloriesLong += post.nutritionInfo?.NUTR_CONT1?.takeIf { it.isNotBlank() }
                ?.toDoubleOrNull() ?: 0.0
            carbLong += post.nutritionInfo?.NUTR_CONT2?.takeIf { it.isNotBlank() }?.toDoubleOrNull()
                ?: 0.0
            proteinLong += post.nutritionInfo?.NUTR_CONT3?.takeIf { it.isNotBlank() }
                ?.toDoubleOrNull() ?: 0.0
            fatLong += post.nutritionInfo?.NUTR_CONT4?.takeIf { it.isNotBlank() }?.toDoubleOrNull()
                ?: 0.0
            sugarsLong += post.nutritionInfo?.NUTR_CONT5?.takeIf { it.isNotBlank() }
                ?.toDoubleOrNull() ?: 0.0
            sodiumLong += post.nutritionInfo?.NUTR_CONT6?.takeIf { it.isNotBlank() }
                ?.toDoubleOrNull() ?: 0.0
            cholesterolLong += post.nutritionInfo?.NUTR_CONT7?.takeIf { it.isNotBlank() }
                ?.toDoubleOrNull() ?: 0.0
            saturatedFatLong += post.nutritionInfo?.NUTR_CONT8?.takeIf { it.isNotBlank() }
                ?.toDoubleOrNull() ?: 0.0
            transFatLong += post.nutritionInfo?.NUTR_CONT9?.takeIf { it.isNotBlank() }
                ?.toDoubleOrNull() ?: 0.0
        }
        Log.d("Nutrition", "$servingSizeLong, $caloriesLong, $carbLong, $proteinLong, $fatLong")
        return Nutrition(
            String.format("%.1f", servingSizeLong),
            String.format("%.1f", caloriesLong),
            String.format("%.1f", carbLong),
            String.format("%.1f", proteinLong),
            String.format("%.1f", fatLong),
            String.format("%.1f", sugarsLong),
            String.format("%.1f", sodiumLong),
            String.format("%.1f", cholesterolLong),
            String.format("%.1f", saturatedFatLong),
            String.format("%.1f", transFatLong)
        )
    }

    suspend fun getRecentSevenDaysPost(): LineData {
        val entries = ArrayList<Entry>()
        val weekPosts = repository.getRecentSevenDaysPost()
        val sortedWeekPosts = weekPosts.toSortedMap()
        var index = 0f
        for (post in sortedWeekPosts) {
            val calories = post.value.nutritionInfo?.NUTR_CONT1

            if (calories != null) {
                val entry = Entry(index, calories.toFloat())
                entries.add(entry)
            }
            index += 1f
        }

        val dataSet = LineDataSet(entries, "Calories")
        dataSet.lineWidth = 3f
        dataSet.valueTextSize = 11f
        return LineData(dataSet)
    }


    companion object {
        fun provideFactory(repository: ChartRepository) = viewModelFactory {
            initializer {
                ChartViewModel(repository)
            }
        }
    }

}