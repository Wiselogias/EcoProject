package com.example.ecoproject.data.repositories

import androidx.paging.PagingSource
import com.example.ecoproject.data.db.dao.FavoriteArticleDao
import com.example.ecoproject.data.db.mappers.FavoriteArticleMapper
import com.example.ecoproject.data.pagingsource.FavoriteArticlePagingSource
import com.example.ecoproject.domain.entities.ArticleEntity
import com.example.ecoproject.domain.repositories.FavoriteArticleRepo
import javax.inject.Inject

class FavoriteArticleRepoImpl @Inject constructor(
    private val favoriteArticleDao: FavoriteArticleDao
) : FavoriteArticleRepo {
    override suspend fun addArticle(articleEntity: ArticleEntity): ArticleEntity {
        favoriteArticleDao.upsert(FavoriteArticleMapper.fromDomainEntityToRoomEntity(articleEntity))
        return articleEntity
    }

    override suspend fun getArticle(id: String): ArticleEntity = FavoriteArticleMapper
        .fromRoomEntityToDomainEntity(favoriteArticleDao.getArticle(id))

    override suspend fun getArticles(): List<ArticleEntity> =
        favoriteArticleDao.getArticles()
            .map { FavoriteArticleMapper.fromRoomEntityToDomainEntity(it) }

    override suspend fun deleteArticle(id: String): ArticleEntity {
        val deleted = favoriteArticleDao.getArticle(id)
        favoriteArticleDao.delete(deleted)
        return FavoriteArticleMapper.fromRoomEntityToDomainEntity(deleted)
    }


    override fun getArticlesPaged(): PagingSource<Int, ArticleEntity> =
        FavoriteArticlePagingSource(favoriteArticleDao)

}