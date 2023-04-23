package com.example.ecoproject.data.db.di

import android.content.Context
import androidx.room.Room
import com.example.ecoproject.data.db.dao.ArticleDao
import com.example.ecoproject.data.db.dao.FavoriteArticleDao
import com.example.ecoproject.data.db.dao.FavoritePointDao
import com.example.ecoproject.data.db.dao.PointDao
import com.example.ecoproject.data.db.database.AppDataBase
import com.example.ecoproject.data.di.DataScope
import dagger.Module
import dagger.Provides


@Module
class DatabaseModule {
    @Provides
    @DataScope
    fun provideDatabase(context: Context): AppDataBase = Room
            .databaseBuilder(context, AppDataBase::class.java, "ecoProjectDB")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @DataScope
    fun provideArticleDao(appDataBase: AppDataBase): ArticleDao = appDataBase.getArticleDao()

    @Provides
    @DataScope
    fun provideFavoriteArticleDao(appDataBase: AppDataBase): FavoriteArticleDao  = appDataBase.getFavoriteArticleDao()

    @Provides
    @DataScope
    fun providePointDao(appDataBase: AppDataBase): PointDao = appDataBase.getPointDao()

    @Provides
    @DataScope
    fun provideFavoritePointDao(appDataBase: AppDataBase): FavoritePointDao = appDataBase.getFavoritePointDao()

}