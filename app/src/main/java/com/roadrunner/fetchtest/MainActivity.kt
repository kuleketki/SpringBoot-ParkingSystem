package com.roadrunner.fetchtest

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.roadrunner.fetchtest.adapters.ItemAdapter
import com.roadrunner.fetchtest.data.ItemRepository
import com.roadrunner.fetchtest.data.RetrofitClient
import com.roadrunner.fetchtest.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

const val TAG = "MainActivity"
// MainActivity.kt

/**
 * Main activity responsible for displaying a list of items fetched from a remote API.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var itemAdapter: ItemAdapter
    private lateinit var itemRepository: ItemRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        itemRepository = ItemRepository(RetrofitClient.itemApiService)

        setupRecyclerView()

        // Load data asynchronously
        lifecycleScope.launch {
            loadData()
        }
    }

    /**
     * Configures the RecyclerView to display items.
     */
    private fun setupRecyclerView() = binding.recyclerView.apply {
        itemAdapter = ItemAdapter()
        adapter = itemAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
    }

    /**
     * Asynchronously loads items from the remote API and updates the UI.
     */
    private suspend fun loadData() {
        withContext(Dispatchers.IO) {
            try {
                binding.progressBar.isVisible = true
                val items = itemRepository.getItemsList()
                withContext(Dispatchers.Main) {
                    itemAdapter.items = items
                }

                binding.progressBar.isVisible = false
            } catch (e: Exception) {
                // Handle other generic exceptions
                Log.e(TAG, "Unexpected error")
                binding.progressBar.isVisible = false
                e.printStackTrace()
            }
        }
    }
}