package com.example.ecoproject.ui.pointsadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.example.ecoproject.databinding.PointItemBinding

class PointItemAdapter(
    private val onItemClick: (Item) -> Unit
) : PagingDataAdapter<Item, PointItemViewHolder>(Item.DIFF) {
    override fun onBindViewHolder(holder: PointItemViewHolder, position: Int) {
        holder.bindNullable(getItem(position), onItemClick)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PointItemViewHolder(
        PointItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )
}