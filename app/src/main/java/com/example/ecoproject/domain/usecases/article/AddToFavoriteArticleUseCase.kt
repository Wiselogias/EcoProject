package com.example.ecoproject.domain.usecases.article

import com.example.ecoproject.domain.entities.ArticleEntity
import com.example.ecoproject.domain.repositories.ArticleRepo
import com.example.ecoproject.domain.repositories.FavoriteArticleRepo
import com.example.ecoproject.domain.usecases.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddToFavoriteArticleUseCase @Inject constructor(
    private val favoriteArticleRepo: FavoriteArticleRepo,
    private val articleRepo: ArticleRepo
) : UseCase<AddToFavoriteArticleParams, ArticleEntity> {
    override fun invoke(input: AddToFavoriteArticleParams): Flow<ArticleEntity> = flow {
        emit(favoriteArticleRepo.addArticle(articleRepo.getArticle(input.id)))
    }
}

data class AddToFavoriteArticleParams(
    val id: String
)