package com.example.fooding.ui.addfood

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.fooding.data.model.Nutrition
import com.example.fooding.data.source.AddFoodRepository
import kotlinx.coroutines.launch

class AddFoodViewModel(private val repository: AddFoodRepository) : ViewModel() {

    val memo = MutableLiveData<String>()
    private val _title = MutableLiveData<String>()
    val title: LiveData<String> get() = _title
    private val _date = MutableLiveData<Long>()
    val date: LiveData<Long> get() = _date
    private val _time = MutableLiveData<String>()
    val time: LiveData<String> get() = _time
    private var category: String? = null
    var nutritionInfo: Nutrition? = null

    private val _imgUri: MutableLiveData<Uri> = MutableLiveData()
    private val imgUri: LiveData<Uri> = _imgUri

    fun notifyRadioButtonChanged(tag: String) {
        category = tag
    }

    fun notifyImgChange(uri: Uri) {
        _imgUri.value = uri
    }

    fun uploadImg() {
        val imgUriValue = imgUri.value
        if (imgUriValue != null) {
            viewModelScope.launch {
                repository.uploadImg(imgUriValue)
            }
        }
    }

    fun uploadPost() {
        val titleValue = title.value
        val dateValue = date.value
        val timeValue = time.value
        val memoValue = memo.value
        val imgUriValue = imgUri.value
        val categoryValue = category
        val nutritionValue = nutritionInfo
        viewModelScope.launch {
            repository.uploadPost(titleValue, dateValue, timeValue, categoryValue, memoValue, imgUriValue, nutritionValue)
        }
    }

    fun setDate(date: Long) {
        _date.value = date
    }

    fun setTime(time: String) {
        _time.value = time
    }

    fun setNutrition(nutrition: Nutrition) {
        nutritionInfo = nutrition
    }

    companion object {
        fun provideFactory(repository: AddFoodRepository) = viewModelFactory {
            initializer {
                AddFoodViewModel(repository)
            }
        }
    }
}