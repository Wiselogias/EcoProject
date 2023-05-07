package com.example.ecoproject.ui.article

import com.example.ecoproject.common.mvvm.BaseViewModel
import com.example.ecoproject.domain.usecases.article.AddToFavoriteArticleParams
import com.example.ecoproject.domain.usecases.article.AddToFavoriteArticleUseCase
import com.example.ecoproject.domain.usecases.article.GetArticleParams
import com.example.ecoproject.domain.usecases.article.GetArticleUseCase
import com.example.ecoproject.domain.usecases.article.IsFavoriteArticleParams
import com.example.ecoproject.domain.usecases.article.IsFavoriteArticleUseCase
import com.example.ecoproject.domain.usecases.article.RemoveFromFavoriteArticleParams
import com.example.ecoproject.domain.usecases.article.RemoveFromFavoriteArticleUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import javax.inject.Inject

class ArticleViewModel @Inject constructor(
    private val addToFavoriteArticleUseCase: AddToFavoriteArticleUseCase,
    private val removeFromFavoriteArticleUseCase: RemoveFromFavoriteArticleUseCase,
    getArticleUseCase: GetArticleUseCase,
    isFavoriteArticleUseCase: IsFavoriteArticleUseCase,

) : BaseViewModel() {
    val articleID = MutableSharedFlow<String>(1)

    val article = articleID.flatMapConcat {
        combine(
            getArticleUseCase(GetArticleParams(it)),
            isFavoriteArticleUseCase(IsFavoriteArticleParams(it)),
        ) { article, isFavorite ->
            article to isFavorite
        }
    }.shareWhileSubscribed()

    fun addToFavorite() = articleID.take(1).flatMapConcat {
        addToFavoriteArticleUseCase(AddToFavoriteArticleParams(it))
    }.onEach {
        articleID.emit(it.id)
    }

    fun removeFromFavorite() = articleID.take(1).flatMapConcat {
        removeFromFavoriteArticleUseCase(RemoveFromFavoriteArticleParams(it))
    }.onEach {
        articleID.emit(it.id)
    }


}