package com.example.fooding

import com.example.fooding.data.source.remote.PostApiClient
import com.example.fooding.data.source.remote.FoodApiClient

class AppContainer {

    private var postApiClient: PostApiClient? = null
    private var foodApiClient: FoodApiClient? = null

    fun providePostApiClient(): PostApiClient {
        return postApiClient ?: PostApiClient.create().apply {
            postApiClient = this
        }
    }

    fun provideFoodApiClient(): FoodApiClient {
        return foodApiClient ?: FoodApiClient.create().apply {
            foodApiClient = this
        }
    }
}