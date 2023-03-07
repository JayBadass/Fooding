package com.example.fooding

import android.app.Application

class FoodingApplication : Application() {

    companion object {
        lateinit var appContainer: AppContainer
    }

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer()
    }
}