package com.example.ecoproject.common.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<T, B : ViewBinding>(
    protected val binding: B,
) : RecyclerView.ViewHolder(binding.root) {

    abstract fun bind(model: T)

    open fun bind(model: T, onClick: (T) -> Unit) {
        binding.root.setOnClickListener {
            onClick(model)
        }
        bind(model)
    }

    fun bindNullable(model: T?, onClick: (T) -> Unit) {
        if (model != null) bind(model, onClick)
    }
}