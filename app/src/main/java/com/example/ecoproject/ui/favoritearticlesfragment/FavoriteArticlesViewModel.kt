package com.example.ecoproject.ui.favoritearticlesfragment

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.ecoproject.common.mvvm.BaseViewModel
import com.example.ecoproject.domain.usecases.article.GetFavoriteArticlesPagedUseCase
import com.example.ecoproject.ui.articlesadapter.Item
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteArticlesViewModel @Inject constructor(
    getFavoriteArticlesUseCase: GetFavoriteArticlesPagedUseCase
) : BaseViewModel() {
    val articles = getFavoriteArticlesUseCase(
        PagingConfig(
            pageSize = 20,
            initialLoadSize = 20,
            enablePlaceholders = false
        )
    ).cachedIn(viewModelScope).map { pagingData ->
        pagingData.map {
            Item(it.id, it.title, it.author, it.imageReference)
        }
    }.shareWhileSubscribed()
}