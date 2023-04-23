package com.example.ecoproject.data.db.dao

import androidx.paging.PagingSource
import androidx.room.Query
import com.example.ecoproject.data.db.entities.FavoriteArticleRoomEntity

abstract class FavoriteArticleDao : CommonDao<FavoriteArticleRoomEntity>() {

    @Query("SELECT * FROM favorite_articles ORDER BY dateTime DESC")
    abstract suspend fun getArticles(): List<FavoriteArticleRoomEntity>

    @Query("SELECT * FROM  favorite_articles BY dateTime DESC")
    abstract fun getArticlesPaged() : PagingSource<Int, FavoriteArticleRoomEntity>

    @Query("SELECT * FROM favorite_articles WHERE id = :id")
    abstract suspend fun getArticle(id: String): FavoriteArticleRoomEntity
}