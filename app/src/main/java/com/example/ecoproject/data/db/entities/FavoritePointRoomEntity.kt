package com.example.ecoproject.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_points")
data class FavoritePointRoomEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo
    val address: String,
    @ColumnInfo
    val lat: Double,
    @ColumnInfo
    val lon: Double
)
