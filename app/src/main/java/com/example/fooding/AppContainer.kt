package com.example.fooding

import com.example.fooding.data.source.remote.ApiClient

class AppContainer {

    private var apiClient: ApiClient? = null

    fun provideApiClient(): ApiClient {
        return apiClient ?: ApiClient.create().apply {
            apiClient = this
        }
    }
}