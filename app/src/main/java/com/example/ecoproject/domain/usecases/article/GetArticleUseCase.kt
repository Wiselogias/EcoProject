package com.example.ecoproject.domain.usecases.article

import com.example.ecoproject.domain.entities.ArticleEntity
import com.example.ecoproject.domain.repositories.ArticleRepo
import com.example.ecoproject.domain.usecases.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetArticleUseCase @Inject constructor(
    private val articleRepo: ArticleRepo
) : UseCase<GetArticleParams, ArticleEntity> {
    override fun invoke(input: GetArticleParams): Flow<ArticleEntity> = flow {
        emit(articleRepo.getArticle(input.id))
    }
}

data class GetArticleParams(
    val id: String
)