package com.example.ecoproject.ui.pointsadapter

import androidx.recyclerview.widget.DiffUtil

data class Item(
    val id: String,
    val address: String
) {
    companion object {
        val DIFF = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean =
                oldItem == newItem
        }
    }
}
