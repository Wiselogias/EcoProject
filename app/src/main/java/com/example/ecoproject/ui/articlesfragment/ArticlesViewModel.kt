package com.example.ecoproject.ui.articlesfragment

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.ecoproject.common.mvvm.BaseViewModel
import com.example.ecoproject.domain.usecases.article.GetArticlesPagedUseCase
import com.example.ecoproject.ui.articlesadapter.Item
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArticlesViewModel @Inject constructor(
    getArticlesPagedUseCase: GetArticlesPagedUseCase
) : BaseViewModel() {

    val articles = getArticlesPagedUseCase(
        PagingConfig(
            pageSize = 50,
            initialLoadSize = 50,
            enablePlaceholders = false
        )
    ).cachedIn(viewModelScope).map { pagingData ->
        pagingData.map {
            Item(it.id, it.title, it.author, it.imageReference)
        }
    }.shareWhileSubscribed()
}