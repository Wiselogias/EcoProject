package com.example.ecoproject.data.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.example.ecoproject.data.db.entities.FavoritePointRoomEntity

@Dao
abstract class FavoritePointDao : CommonDao<FavoritePointRoomEntity>() {

    @Query("SELECT * FROM favorite_points ORDER BY address DESC")
    abstract suspend fun getPoints(): List<FavoritePointRoomEntity>

    @Query("SELECT * FROM favorite_points")
    abstract fun getPointsPaged(): PagingSource<Int, FavoritePointRoomEntity>

    @Query("SELECT * FROM favorite_points WHERE id = :id")
    abstract suspend fun getPoint(id: String): FavoritePointRoomEntity
}