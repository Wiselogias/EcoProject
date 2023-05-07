package com.example.ecoproject.data.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.ecoproject.data.db.dao.ArticleDao
import com.example.ecoproject.data.db.mappers.ArticleMapper
import com.example.ecoproject.data.firebase.datasource.ArticleDataSource
import com.example.ecoproject.data.mediator.ArticlesMediator
import com.example.ecoproject.domain.entities.ArticleEntity
import com.example.ecoproject.domain.repositories.ArticleRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArticleRepoImpl @Inject constructor(
    private val articleDao: ArticleDao,
    private val firebaseDataSource: ArticleDataSource,
    private val articlesMediator: ArticlesMediator
) : ArticleRepo {
    override suspend fun createArticle(article: ArticleEntity): ArticleEntity {
        val remoteArticle = firebaseDataSource.saveArticle(article)
        articleDao.upsert(ArticleMapper.fromDomainEntityToRoomEntity(remoteArticle))
        return remoteArticle
    }

    override suspend fun getArticle(id: String): ArticleEntity =
        ArticleMapper.fromRoomEntityToDomainEntity(articleDao.getArticle(id))

    override suspend fun getArticles(): List<ArticleEntity> =
        articleDao.getArticles().map { ArticleMapper.fromRoomEntityToDomainEntity(it) }

    @OptIn(ExperimentalPagingApi::class)
    override fun getArticlesPaged(pagingConfig: PagingConfig): Flow<PagingData<ArticleEntity>> =
        Pager(
            config = pagingConfig,
            remoteMediator = articlesMediator
        ) {
            articleDao.getArticlesPaged()
        }.flow.map { pagingData ->
            pagingData.map {
                ArticleMapper.fromRoomEntityToDomainEntity(it)
            }
        }


}