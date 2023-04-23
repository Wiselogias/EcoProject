package com.example.ecoproject.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "points")
data class PointRoomEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo
    val address: String,
    @ColumnInfo
    val lat: Double,
    @ColumnInfo
    val lon: Double
)
