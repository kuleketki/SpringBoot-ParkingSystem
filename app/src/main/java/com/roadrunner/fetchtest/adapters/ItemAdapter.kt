package com.roadrunner.fetchtest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.roadrunner.fetchtest.databinding.ItemViewBinding
import com.roadrunner.fetchtest.model.Item

/**
 * RecyclerView adapter for displaying a list of [Item] objects.
 *
 *
 * items: The list of [Item] objects to be displayed.
 * Creates an instance of [ItemAdapter].
 */
class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ItemViewHolder> (){
    inner class ItemViewHolder(val binding: ItemViewBinding) : RecyclerView.ViewHolder(binding.root)

    // Callback for calculating the difference between two lists of items
    private val diffCallback = object: DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }

    // AsyncListDiffer to efficiently compute differences between two lists on a background thread
    private val differ = AsyncListDiffer(this,diffCallback)
    var items: List<Item>
        get() = differ.currentList
        // Filter and sort the items before submitting to the differ
        set(value) {
            val filteredAndSortedItems = value
                .filter { it.name?.isNotBlank() == true }
                .sortedWith(compareBy({ it.listId }, { it.name.trim() }))

            differ.submitList(filteredAndSortedItems)
        }

    override fun getItemCount() = items.size

    // Creates a new ViewHolder for an item view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ItemViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    // Binds data to the ViewHolder
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.binding.apply {
            val item = items[position]
            tvItemId.text = item.id.toString()
            tvListId.text = item.listId.toString()
            tvItemName.text = item.name


        }
    }

}