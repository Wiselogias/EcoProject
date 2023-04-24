package com.example.ecoproject.domain.usecases.article

import com.example.ecoproject.domain.entities.ArticleEntity
import com.example.ecoproject.domain.repositories.ArticleRepo
import com.example.ecoproject.domain.usecases.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.joda.time.DateTime
import javax.inject.Inject

class CreateArticleUseCase @Inject constructor(
    private val articleRepo: ArticleRepo
) : UseCase<CreateArticleParams, CreateArticleResult> {
    override fun invoke(input: CreateArticleParams): Flow<CreateArticleResult> = flow {
        try {
            emit(
                CreateArticleResult.Success(
                    articleRepo.createArticle(
                        ArticleEntity(
                            id = "",
                            text = input.text,
                            title = input.title,
                            imageReference = input.imageReference,
                            time = DateTime(),
                            author = "user"
                        )
                    )
                )
            )
        } catch (e: Exception) {
            emit(CreateArticleResult.Error(e))
        }
    }
}

data class CreateArticleParams(
    val title: String,
    val text: String,
    val imageReference: String,
)

sealed class CreateArticleResult {
    data class Success(
        val articleEntity: ArticleEntity
    ) : CreateArticleResult()

    data class Error(
        val t: Throwable
    ) : CreateArticleResult()
}