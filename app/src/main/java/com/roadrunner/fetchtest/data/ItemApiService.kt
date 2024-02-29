package com.roadrunner.fetchtest.data

import com.roadrunner.fetchtest.model.Item
import retrofit2.Response
import retrofit2.http.GET

/**
 * Interface defining the API service for fetching items.
 */
interface ItemApiService {

    @GET("/hiring.json")
    suspend fun getItemsList(): Response<List<Item>>
}