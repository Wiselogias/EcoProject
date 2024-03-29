package com.example.ecoproject.data.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.example.ecoproject.data.db.entities.ArticleRoomEntity

@Dao
abstract class ArticleDao : CommonDao<ArticleRoomEntity>() {

    @Query("SELECT * FROM articles ORDER BY time DESC")
    abstract suspend fun getArticles(): List<ArticleRoomEntity>

    @Query("SELECT * FROM articles ORDER BY time DESC LIMIT :limit OFFSET :skip")
    abstract fun getArticlesPaged(limit: Int, skip: Int): List<ArticleRoomEntity>

    @Query("SELECT * FROM articles ORDER BY time DESC")
    abstract fun getArticlesPaged(): PagingSource<Int, ArticleRoomEntity>

    @Query("SELECT * FROM articles WHERE id = :id")
    abstract suspend fun getArticle(id: String): ArticleRoomEntity
}