package com.example.ecoproject.domain.usecases.article

import com.example.ecoproject.domain.entities.ArticleEntity
import com.example.ecoproject.domain.repositories.FavoriteArticleRepo
import com.example.ecoproject.domain.usecases.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFavoriteArticleUseCase @Inject constructor(
    private val favoriteArticleRepo: FavoriteArticleRepo
) : UseCase<GetFavoriteArticleParams, ArticleEntity> {
    override fun invoke(input: GetFavoriteArticleParams): Flow<ArticleEntity> = flow {
        emit(favoriteArticleRepo.getArticle(input.id))
    }
}

data class GetFavoriteArticleParams(
    val id: String
)