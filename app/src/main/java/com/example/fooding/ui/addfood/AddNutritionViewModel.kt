package com.example.fooding.ui.addfood

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fooding.data.model.Nutrition

class AddNutritionViewModel : ViewModel() {

    var nutrition: Nutrition? = null

    val foodName = MutableLiveData<String>()
    val servingSize = MutableLiveData<String>()
    val calories = MutableLiveData<String>()
    val carb = MutableLiveData<String>()
    val protein = MutableLiveData<String>()
    val fat = MutableLiveData<String>()
    val sugar = MutableLiveData<String>()
    val sodium = MutableLiveData<String>()
    val cholesterol = MutableLiveData<String>()
    val saturatedFat = MutableLiveData<String>()
    val transFat = MutableLiveData<String>()

    fun serializeNutrition() {
        nutrition = Nutrition(
            foodName.value,
            servingSize.value,
            calories.value,
            carb.value,
            protein.value,
            fat.value,
            sugar.value,
            sodium.value,
            cholesterol.value,
            saturatedFat.value,
            transFat.value
        )
    }
}