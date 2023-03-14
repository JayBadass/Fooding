package com.example.fooding.ui.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.fooding.data.model.Post
import com.example.fooding.data.source.CalendarRepository
import com.example.fooding.util.DateFormatText
import java.text.SimpleDateFormat
import java.util.*

class CalendarViewModel(private val repository: CalendarRepository) : ViewModel() {

    suspend fun loadPost(date: Long): List<Post> {
        val posts = repository.getPostByDate("\"date\"", date, date)
        return posts.values.toList()
    }

    suspend fun updateCalendarEvent(): List<Post> {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val startDateString = "2023-03-01"
        val endDateString = DateFormatText.getCurrentDate()
        val startDateLong = dateFormat.parse(startDateString)!!.time
        val endDateLong = dateFormat.parse(endDateString)!!.time
        return repository.getPostByDate("\"date\"", startDateLong, endDateLong).values.toList()
    }

    companion object {
        fun provideFactory(repository: CalendarRepository) = viewModelFactory {
            initializer {
                CalendarViewModel(repository)
            }
        }
    }
}

