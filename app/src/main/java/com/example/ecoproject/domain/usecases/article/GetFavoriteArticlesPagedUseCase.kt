package com.example.ecoproject.domain.usecases.article

import androidx.paging.PagingSource
import com.example.ecoproject.domain.entities.ArticleEntity
import com.example.ecoproject.domain.repositories.FavoriteArticleRepo
import com.example.ecoproject.domain.usecases.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetFavoriteArticlesPagedUseCase @Inject constructor(
    private val favoriteArticleRepo: FavoriteArticleRepo
) : UseCase<Unit, PagingSource<Int, ArticleEntity>> {
    override fun invoke(input: Unit): Flow<PagingSource<Int, ArticleEntity>> =
        flowOf(favoriteArticleRepo.getArticlesPaged())
}