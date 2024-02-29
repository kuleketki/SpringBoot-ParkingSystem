package com.roadrunner.fetchtest.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Singleton object for creating and accessing the Retrofit client instance.
 * It provides a lazily initialized instance of [ItemApiService] for making network requests.
 */

object RetrofitClient {
    private const val BASE_URL = "https://fetch-hiring.s3.amazonaws.com/"

    val itemApiService: ItemApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ItemApiService::class.java)
    }
}