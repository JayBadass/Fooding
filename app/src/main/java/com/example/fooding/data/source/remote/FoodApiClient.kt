package com.example.fooding.data.source.remote

import com.example.fooding.data.model.FoodResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface FoodApiClient {

    @GET("api/{serviceKey}/I2790/json/1/10/DESC_KOR={foodName}")
    suspend fun getFoodList(
        @Path("serviceKey") serviceKey: String,
        @Path("foodName") foodName: String,
    ): FoodResponse

    companion object {
        private const val BASE_URL = "http://openapi.foodsafetykorea.go.kr/"
        private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

        fun create(): FoodApiClient {

            val logger = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
                .client(client)
                .build()
                .create(FoodApiClient::class.java)
        }
    }
}