package com.example.ecoproject.data.repositories

import androidx.paging.PagingSource
import com.example.ecoproject.data.db.dao.ArticleDao
import com.example.ecoproject.data.db.mappers.ArticleMapper
import com.example.ecoproject.data.firebase.datasource.ArticleDataSource
import com.example.ecoproject.data.pagingsource.ArticlePagingSource
import com.example.ecoproject.domain.entities.ArticleEntity
import com.example.ecoproject.domain.repositories.ArticleRepo
import javax.inject.Inject

class ArticleRepoImpl @Inject constructor(
    private val articleDao: ArticleDao,
    private val firebaseDataSource: ArticleDataSource
) : ArticleRepo {
    override suspend fun createArticle(article: ArticleEntity): ArticleEntity {
        val firebaseArticle = firebaseDataSource.saveArticle(article)
        val remoteArticle = article.copy(id = firebaseArticle.id)
        articleDao.upsert(ArticleMapper.fromDomainEntityToRoomEntity(remoteArticle))
        return remoteArticle
    }

    override suspend fun getArticle(id: String): ArticleEntity =
        ArticleMapper.fromRoomEntityToDomainEntity(articleDao.getArticle(id))

    override suspend fun getArticles(): List<ArticleEntity> =
        articleDao.getArticles().map { ArticleMapper.fromRoomEntityToDomainEntity(it) }

    override fun getArticlesPaged(): PagingSource<Int, ArticleEntity> =
        ArticlePagingSource(firebaseDataSource, articleDao)
}