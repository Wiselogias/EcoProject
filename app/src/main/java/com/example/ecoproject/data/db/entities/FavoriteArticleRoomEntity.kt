package com.example.ecoproject.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.ecoproject.data.db.database.DateTimeTypeConverter
import org.joda.time.DateTime

@Entity(tableName = "favorite_articles")
@TypeConverters(value = [DateTimeTypeConverter::class])
data class FavoriteArticleRoomEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo
    val title: String,
    @ColumnInfo
    val text: String,
    @ColumnInfo
    val imageReference: String,
    @ColumnInfo
    val time: DateTime
)
