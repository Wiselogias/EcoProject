package com.example.ecoproject.data.db.dao

import androidx.room.Query
import com.example.ecoproject.data.db.entities.FavoritePointRoomEntity

abstract class FavoritePointDao : CommonDao<FavoritePointRoomEntity>() {

    @Query("SELECT * FROM favorite_points ORDER BY address DESC")
    abstract suspend fun getPoints(): List<FavoritePointRoomEntity>

    @Query("SELECT * FROM favorite_points LIMIT :limit OFFSET :skip")
    abstract fun getPointsPaged(limit: Int, skip: Int): List<FavoritePointRoomEntity>

    @Query("SELECT * FROM favorite_points WHERE id = :id")
    abstract suspend fun getPoint(id: String): FavoritePointRoomEntity
}