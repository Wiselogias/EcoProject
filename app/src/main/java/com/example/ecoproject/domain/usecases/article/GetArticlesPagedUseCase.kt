package com.example.ecoproject.domain.usecases.article

import androidx.paging.PagingSource
import com.example.ecoproject.domain.entities.ArticleEntity
import com.example.ecoproject.domain.repositories.ArticleRepo
import com.example.ecoproject.domain.usecases.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetArticlesPagedUseCase @Inject constructor(
    private val articleRepo: ArticleRepo
) : UseCase<Unit, PagingSource<Int, ArticleEntity>> {
    override fun invoke(input: Unit): Flow<PagingSource<Int, ArticleEntity>> =
        flowOf(articleRepo.getArticlesPaged())
}