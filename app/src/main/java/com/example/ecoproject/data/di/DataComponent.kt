package com.example.ecoproject.data.di

import com.example.ecoproject.app.di.AppComponent
import com.example.ecoproject.domain.repositories.ArticleRepo
import com.example.ecoproject.domain.repositories.AuthRepo
import com.example.ecoproject.domain.repositories.FavoriteArticleRepo
import com.example.ecoproject.domain.repositories.FavoritePointRepo
import com.example.ecoproject.domain.repositories.PointRepo
import dagger.Component

@DataScope
@Component(
    dependencies = [
        AppComponent::class
    ],
    modules = [
        DataModule::class
    ]

)
interface DataComponent {
    val articleRepo: ArticleRepo
    val favoriteArticleRepo: FavoriteArticleRepo
    val pointRepo: PointRepo
    val favoritePointRepo: FavoritePointRepo
    val authRepo: AuthRepo
}