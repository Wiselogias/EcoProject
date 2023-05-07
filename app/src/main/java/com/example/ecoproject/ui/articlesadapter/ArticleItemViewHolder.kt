package com.example.ecoproject.ui.articlesadapter

import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import com.bumptech.glide.Glide
import com.example.ecoproject.R
import com.example.ecoproject.common.adapter.BaseViewHolder
import com.example.ecoproject.databinding.ArticleCardBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class ArticleItemViewHolder(
    binding: ArticleCardBinding
) : BaseViewHolder<Item, ArticleCardBinding>(binding) {
    override fun bind(model: Item) {
        binding.titleView.text = model.title
        if (model.imageRef.isEmpty()) {
            binding.imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
            binding.imageView.setImageDrawable(
                AppCompatResources.getDrawable(
                    binding.root.context,
                    R.drawable.round_hide_image_24
                )
            )
        } else {
            binding.imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            Glide
                .with(binding.imageView)
                .load(Firebase.storage.reference.child(model.imageRef))
                .centerCrop()
                .into(binding.imageView)
        }

    }

}