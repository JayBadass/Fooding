package com.example.fooding.data.source.remote

import com.example.fooding.data.model.Converters
import com.example.fooding.data.model.Post
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiClient {

    @POST("post.json")
    suspend fun uploadPost(@Body post: Post)

    companion object {
        private const val baseUrl =
            "https://fooding-a09ef-default-rtdb.asia-southeast1.firebasedatabase.app"

        fun create(): ApiClient {

            val moshi = Moshi.Builder().add(Converters()).build()

            val logger = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(client)
                .build()
                .create(ApiClient::class.java)
        }
    }
}