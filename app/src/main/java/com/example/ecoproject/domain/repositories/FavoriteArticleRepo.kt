package com.example.ecoproject.domain.repositories

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.ecoproject.domain.entities.ArticleEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteArticleRepo {
    suspend fun addArticle(articleEntity: ArticleEntity): ArticleEntity
    suspend fun getArticle(id: String): ArticleEntity
    suspend fun getArticles(): List<ArticleEntity>
    suspend fun deleteArticle(id: String): ArticleEntity
    fun getArticlesPaged(pagingConfig: PagingConfig): Flow<PagingData<ArticleEntity>>
}