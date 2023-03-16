package com.example.fooding.util

import java.text.SimpleDateFormat
import java.util.*

object DateFormatText {

    private const val DATE_YEAR_MONTH_DAY_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    private const val DATE_YEAR_MONTH_PATTERN = "yyyy-MM-dd"

    private val currentLocale
        get() = SystemConfiguration.currentLocale

    fun convertToDate(dateString: String): Date? {
        return SimpleDateFormat(DATE_YEAR_MONTH_DAY_TIME_PATTERN, currentLocale).parse(dateString)
    }

    fun convertToNewDateFormat(newPattern: String, date: Date): String {
        return SimpleDateFormat(newPattern, currentLocale).format(date)
    }

    fun getCurrentTime(): String {
        val formatter = SimpleDateFormat(DATE_YEAR_MONTH_DAY_TIME_PATTERN, currentLocale)
        val currentDate = Calendar.getInstance(TimeZone.getTimeZone("UTC")).time
        return formatter.format(currentDate)
    }

    fun getCurrentDate(): String {
        val formatter = SimpleDateFormat(DATE_YEAR_MONTH_PATTERN, currentLocale)
        val currentDate = Calendar.getInstance(TimeZone.getTimeZone("UTC")).time
        return formatter.format(currentDate)
    }
}