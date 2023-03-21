package com.example.fooding

import android.app.Application
import com.example.fooding.data.source.local.AppDatabase

class FoodingApplication : Application() {

    companion object {
        lateinit var appContainer: AppContainer
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer()
        database = AppDatabase.getInstance(this)
    }
}