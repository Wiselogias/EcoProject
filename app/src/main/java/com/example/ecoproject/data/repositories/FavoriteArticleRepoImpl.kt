package com.example.ecoproject.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.ecoproject.data.db.dao.FavoriteArticleDao
import com.example.ecoproject.data.db.mappers.FavoriteArticleMapper
import com.example.ecoproject.domain.entities.ArticleEntity
import com.example.ecoproject.domain.repositories.FavoriteArticleRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

    override fun getArticlesPaged(pagingConfig: PagingConfig): Flow<PagingData<ArticleEntity>> =
        Pager(pagingConfig) {
            favoriteArticleDao.getArticlesPaged()
        }.flow.map { pagingData ->
            pagingData.map {
                FavoriteArticleMapper.fromRoomEntityToDomainEntity(it)
            }
        }
}