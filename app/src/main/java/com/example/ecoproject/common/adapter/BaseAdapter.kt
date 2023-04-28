package com.example.ecoproject.common.adapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<T, VH : BaseViewHolder<T, *>>(
    private val diffCallback: DiffUtil.ItemCallback<T>,
    private val onItemClick: (T) -> Unit = { },
) : RecyclerView.Adapter<VH>() {
    var items: List<T> = emptyList()
        set(value) {
            val result = calculateDiff(value)
            field = value
            result.dispatchUpdatesTo(this)
        }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position], onItemClick)
    }

    protected fun calculateDiff(newItems: List<T>): DiffUtil.DiffResult =
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = items.size

            override fun getNewListSize(): Int = newItems.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                diffCallback.areItemsTheSame(items[oldItemPosition], newItems[newItemPosition])

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                diffCallback.areContentsTheSame(items[oldItemPosition], newItems[newItemPosition])

        })
}