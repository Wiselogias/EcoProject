package com.example.ecoproject.ui.favoritepointsfragment

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.ecoproject.common.mvvm.BaseViewModel
import com.example.ecoproject.domain.usecases.point.GetFavoritePointsPagedUseCase
import com.example.ecoproject.ui.pointsadapter.Item
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(
    getFavoritePointsPagedUseCase: GetFavoritePointsPagedUseCase
) : BaseViewModel() {
    val points = getFavoritePointsPagedUseCase(
        PagingConfig(
            pageSize = 50,
            enablePlaceholders = false
        )
    ).cachedIn(viewModelScope).map { pagingData ->
        pagingData.map {
            Item(it.id, it.address)
        }
    }.shareWhileSubscribed()
}