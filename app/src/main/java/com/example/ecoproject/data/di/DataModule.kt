package com.example.ecoproject.data.di

import com.example.ecoproject.data.db.di.DatabaseModule
import com.example.ecoproject.data.firebase.di.FirebaseModule
import com.example.ecoproject.data.repositories.ArticleRepoImpl
import com.example.ecoproject.data.repositories.AuthRepoImpl
import com.example.ecoproject.data.repositories.FavoriteArticleRepoImpl
import com.example.ecoproject.data.repositories.FavoritePointRepoImpl
import com.example.ecoproject.data.repositories.PointRepoImpl
import com.example.ecoproject.domain.repositories.ArticleRepo
import com.example.ecoproject.domain.repositories.AuthRepo
import com.example.ecoproject.domain.repositories.FavoriteArticleRepo
import com.example.ecoproject.domain.repositories.FavoritePointRepo
import com.example.ecoproject.domain.repositories.PointRepo
import dagger.Binds
import dagger.Module

@Module(
    includes = [
        DatabaseModule::class,
        FirebaseModule::class
    ]
)
abstract class DataModule {
    @Binds
    @DataScope
    abstract fun bindArticleRepo(articleRepoImpl: ArticleRepoImpl): ArticleRepo

    @Binds
    @DataScope
    abstract fun bindFavoriteArticleRepo(favoriteArticleRepoImpl: FavoriteArticleRepoImpl): FavoriteArticleRepo

    @Binds
    @DataScope
    abstract fun bindPointRepo(pointRepoImpl: PointRepoImpl): PointRepo

    @Binds
    @DataScope
    abstract fun bindFavoritePointRepo(FavoritePointRepoImpl: FavoritePointRepoImpl): FavoritePointRepo

    @Binds
    @DataScope
    abstract fun bindAuthRepo(authRepoImpl: AuthRepoImpl): AuthRepo
}