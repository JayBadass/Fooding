package com.example.fooding.ui.calendar

import androidx.lifecycle.ViewModel
import com.example.fooding.data.model.Post
import com.example.fooding.data.source.CalendarRepository
import com.example.fooding.util.DateFormatText
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(private val repository: CalendarRepository) :
    ViewModel() {

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
}

