package com.example.ecoproject.domain.repositories

import androidx.paging.PagingSource
import com.example.ecoproject.domain.entities.ArticleEntity

interface ArticleRepo {
    suspend fun createArticle(article: ArticleEntity): ArticleEntity
    suspend fun getArticle(id: String): ArticleEntity
    suspend fun getArticles(): List<ArticleEntity>
    fun getArticlesPaged(): PagingSource<Int, ArticleEntity>
}