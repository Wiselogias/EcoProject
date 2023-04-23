package com.example.ecoproject.data.repositories

import androidx.paging.PagingSource
import com.example.ecoproject.data.db.dao.FavoriteArticleDao
import com.example.ecoproject.domain.entities.ArticleEntity
import com.example.ecoproject.domain.repositories.FavoriteArticleRepo
import javax.inject.Inject

class FavoriteArticleRepoImpl @Inject constructor(
    private val favoriteArticleDao: FavoriteArticleDao
) : FavoriteArticleRepo {
    override suspend fun addArticle(articleEntity: ArticleEntity): ArticleEntity {
        TODO("Not yet implemented")
    }

    override suspend fun getArticle(id: String): ArticleEntity {
        TODO("Not yet implemented")
    }

    override suspend fun getArticles(): List<ArticleEntity> {
        TODO("Not yet implemented")
    }

    override fun getArticlesPaged(): PagingSource<Int, ArticleEntity> {
        TODO("Not yet implemented")
    }

}