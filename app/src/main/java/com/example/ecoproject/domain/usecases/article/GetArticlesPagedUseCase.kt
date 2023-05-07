package com.example.ecoproject.domain.usecases.article

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.ecoproject.domain.entities.ArticleEntity
import com.example.ecoproject.domain.repositories.ArticleRepo
import com.example.ecoproject.domain.usecases.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArticlesPagedUseCase @Inject constructor(
    private val articleRepo: ArticleRepo
) : UseCase<PagingConfig, PagingData<ArticleEntity>> {
    override fun invoke(input: PagingConfig): Flow<PagingData<ArticleEntity>> =
        articleRepo.getArticlesPaged(input)
}