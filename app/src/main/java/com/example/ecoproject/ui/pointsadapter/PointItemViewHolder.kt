package com.example.ecoproject.ui.pointsadapter

import com.example.ecoproject.common.adapter.BaseViewHolder
import com.example.ecoproject.databinding.PointItemBinding

class PointItemViewHolder(
    binding: PointItemBinding
) : BaseViewHolder<Item, PointItemBinding>(binding) {
    override fun bind(model: Item) {
        binding.titleView.text = model.address
    }
}