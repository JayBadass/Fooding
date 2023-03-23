package com.example.fooding.di

import com.example.fooding.data.source.remote.FoodApiClient
import com.example.fooding.data.source.remote.PostApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideFoodApiClient(): FoodApiClient {
        return FoodApiClient.create()
    }

    @Singleton
    @Provides
    fun providePostApiClient(): PostApiClient {
        return PostApiClient.create()
    }
}