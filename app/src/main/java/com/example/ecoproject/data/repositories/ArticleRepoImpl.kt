package com.example.ecoproject.data.repositories

import androidx.paging.PagingSource
import com.example.ecoproject.data.db.dao.ArticleDao
import com.example.ecoproject.data.db.mappers.ArticleMapper
import com.example.ecoproject.domain.entities.ArticleEntity
import com.example.ecoproject.domain.repositories.ArticleRepo
import javax.inject.Inject

class ArticleRepoImpl @Inject constructor(
    private val articleDao: ArticleDao
) : ArticleRepo {
    override suspend fun createArticle(article: ArticleEntity): ArticleEntity {
        //TODO upload to firebase and get id
        val remoteArticle = article.copy() //TODO insert id from firebase
        articleDao.upsert(ArticleMapper.fromDomainEntityToRoomEntity(remoteArticle))
        return remoteArticle
    }

    override suspend fun getArticle(id: String): ArticleEntity =
        ArticleMapper.fromRoomEntityToDomainEntity(articleDao.getArticle(id))

    override suspend fun getArticles(): List<ArticleEntity> =
        //TODO firstly get articles from firebase update local bd and then do this duuude
        articleDao.getArticles().map { ArticleMapper.fromRoomEntityToDomainEntity(it) }

    override fun getArticlesPaged(): PagingSource<Int, ArticleEntity> =
        TODO("think about it")
}