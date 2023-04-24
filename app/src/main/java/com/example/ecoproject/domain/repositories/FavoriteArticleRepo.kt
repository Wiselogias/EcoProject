package com.example.ecoproject.domain.repositories

import androidx.paging.PagingSource
import com.example.ecoproject.domain.entities.ArticleEntity

interface FavoriteArticleRepo {
    suspend fun addArticle(articleEntity: ArticleEntity): ArticleEntity
    suspend fun getArticle(id: String): ArticleEntity
    suspend fun getArticles(): List<ArticleEntity>
    suspend fun deleteArticle(id: String): ArticleEntity
    fun getArticlesPaged(): PagingSource<Int, ArticleEntity>
}