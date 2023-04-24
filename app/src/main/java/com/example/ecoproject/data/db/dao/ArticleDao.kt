package com.example.ecoproject.data.db.dao

import androidx.room.Query
import com.example.ecoproject.data.db.entities.ArticleRoomEntity

abstract class ArticleDao : CommonDao<ArticleRoomEntity>() {

    @Query("SELECT * FROM articles ORDER BY dateTime DESC")
    abstract suspend fun getArticles(): List<ArticleRoomEntity>

    @Query("SELECT * FROM articles ORDER BY dateTime DESC LIMIT :limit OFFSET :skip")
    abstract fun getArticlesPaged(limit: Int, skip: Int): List<ArticleRoomEntity>

    @Query("SELECT * FROM articles WHERE id = :id")
    abstract suspend fun getArticle(id: String): ArticleRoomEntity
}