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
    private var nutritionValue: MutableList<Nutrition> = mutableListOf()
    private val _date = MutableLiveData<String>()
    val date: LiveData<String> get() = _date
    private val _time = MutableLiveData<String>()
    val time: LiveData<String> get() = _time
    var category: String? = null

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
        val timeValue = date.value + time.value
        val memoValue = memo.value
        val imgUriValue = imgUri.value
        val categoryValue = category
        viewModelScope.launch {
            repository.uploadPost(timeValue, categoryValue, memoValue, imgUriValue, nutritionValue)
        }
    }

    fun setDate(date: String) {
        _date.value = date
    }

    fun setTime(time: String) {
        _time.value = time
    }

    companion object {
        fun provideFactory(repository: AddFoodRepository) = viewModelFactory {
            initializer {
                AddFoodViewModel(repository)
            }
        }
    }
}