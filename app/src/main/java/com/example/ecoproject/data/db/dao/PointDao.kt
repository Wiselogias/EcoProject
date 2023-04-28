package com.example.ecoproject.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.ecoproject.data.db.entities.PointRoomEntity

@Dao
abstract class PointDao : CommonDao<PointRoomEntity>() {

    @Query("SELECT * FROM points ORDER BY address DESC")
    abstract suspend fun getPoints(): List<PointRoomEntity>

    @Query("SELECT * FROM points WHERE id = :id")
    abstract suspend fun getPoint(id: String): PointRoomEntity
}