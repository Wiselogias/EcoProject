package com.example.ecoproject.domain.usecases.article

import com.example.ecoproject.domain.repositories.FavoriteArticleRepo
import com.example.ecoproject.domain.usecases.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class IsFavoriteArticleUseCase @Inject constructor(
    private val favoriteArticleRepo: FavoriteArticleRepo
) : UseCase<IsFavoriteArticleParams, Boolean> {
    override fun invoke(input: IsFavoriteArticleParams): Flow<Boolean> = flow {
        emit(favoriteArticleRepo.getArticles().any { it.id == input.id })
    }
}

data class IsFavoriteArticleParams(
    val id: String
)