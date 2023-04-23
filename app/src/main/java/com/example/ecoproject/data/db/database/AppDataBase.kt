package com.example.ecoproject.data.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ecoproject.data.db.dao.ArticleDao
import com.example.ecoproject.data.db.dao.FavoriteArticleDao
import com.example.ecoproject.data.db.dao.FavoritePointDao
import com.example.ecoproject.data.db.dao.PointDao
import com.example.ecoproject.data.db.entities.ArticleRoomEntity
import com.example.ecoproject.data.db.entities.FavoriteArticleRoomEntity
import com.example.ecoproject.data.db.entities.FavoritePointRoomEntity
import com.example.ecoproject.data.db.entities.PointRoomEntity

@Database(
    version = 1,
    entities = [
        ArticleRoomEntity::class,
        FavoriteArticleRoomEntity::class,
        PointRoomEntity::class,
        FavoritePointRoomEntity::class
    ],
    exportSchema = true,
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun getPointDao(): PointDao
    abstract fun getFavoritePointDao(): FavoritePointDao
    abstract fun getArticleDao(): ArticleDao
    abstract fun getFavoriteArticleDao():  FavoriteArticleDao

}