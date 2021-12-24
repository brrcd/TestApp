package com.example.testapi

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiUtils {
    private const val BASE_URL =
        "https://alpha.as50464.net:29870"

    fun createRetrofit(): Api =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkHttpClient())
            .build()
            .create(Api::class.java)

    private fun createOkHttpClient() =
        OkHttpClient.Builder()
            .addInterceptor(
                createLoggingInterceptor()
            )
            .build()

    private fun createLoggingInterceptor() =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
}