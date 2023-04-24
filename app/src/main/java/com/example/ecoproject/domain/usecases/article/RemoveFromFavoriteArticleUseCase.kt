package com.example.ecoproject.domain.usecases.article

import com.example.ecoproject.domain.entities.ArticleEntity
import com.example.ecoproject.domain.repositories.FavoriteArticleRepo
import com.example.ecoproject.domain.usecases.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoveFromFavoriteArticleUseCase @Inject constructor(
    private val favoriteArticleRepo: FavoriteArticleRepo
) : UseCase<RemoveFromFavoriteArticleParams, ArticleEntity> {
    override fun invoke(input: RemoveFromFavoriteArticleParams): Flow<ArticleEntity> = flow {
        emit(favoriteArticleRepo.deleteArticle(input.id))
    }
}

data class RemoveFromFavoriteArticleParams(
    val id: String
)