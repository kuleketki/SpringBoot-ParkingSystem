package com.roadrunner.fetchtest.data

import android.util.Log
import com.roadrunner.fetchtest.model.Item
import java.io.IOException

const val TAG = "ItemRepository"

/**
 * Repository class responsible for handling data operations related to items.
 *
 * itemApiService The API service for fetching items.
 */
class ItemRepository(private val itemApiService: ItemApiService) {
    suspend fun getItemsList(): List<Item> {
        try {
            val response = itemApiService.getItemsList()

            if (response.isSuccessful) {
                return response.body() ?: emptyList()
            } else {
                // Handle HTTP error
                Log.e(TAG, "HTTP error: ${response.code()}, ${response.message()}")
            }
        } catch (e: IOException) {
            // Handle network or IO error
            Log.e(TAG, "Network error or IO exception", e)
        } catch (e: Exception) {
            // Handle other exceptions
            Log.e(TAG, "Unexpected error", e)
        }
        return emptyList()
    }
}