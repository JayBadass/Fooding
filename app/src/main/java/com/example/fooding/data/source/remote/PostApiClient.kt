package com.example.fooding.data.source.remote

import com.example.fooding.BuildConfig
import com.example.fooding.data.model.Converters
import com.example.fooding.data.model.Post
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PostApiClient {

    @POST("post.json")
    suspend fun uploadPost(@Body post: Post)

    @GET("post.json")
    suspend fun getPostByTime(
        @Query("orderBy") orderBy: String,
        @Query("startAt") startAt: Long,
        @Query("endAt") endAt: Long
    ): Map<String, Post>

    @GET("post.json")
    suspend fun getAllPost(): Map<String, Post>

    companion object {
        private const val baseUrl = BuildConfig.DATABASE_URL

        fun create(): PostApiClient {

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
                .create(PostApiClient::class.java)
        }
    }
}