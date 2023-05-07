package com.example.ecoproject.ui.articlesadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.example.ecoproject.databinding.ArticleCardBinding

class ArticleItemAdapter(
    private val onClickListener: (Item) -> Unit
) : PagingDataAdapter<Item, ArticleItemViewHolder>(Item.DIFF_CALLBACK) {
    override fun onBindViewHolder(holder: ArticleItemViewHolder, position: Int) {
        holder.bindNullable(getItem(position), onClickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ArticleItemViewHolder(
        ArticleCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )
}