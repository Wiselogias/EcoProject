package com.example.ecoproject.ui.point

import com.example.ecoproject.common.mvvm.BaseViewModel
import com.example.ecoproject.domain.usecases.point.AddFavoriteParams
import com.example.ecoproject.domain.usecases.point.AddFavoritePointUseCase
import com.example.ecoproject.domain.usecases.point.GetPointParams
import com.example.ecoproject.domain.usecases.point.GetPointUseCase
import com.example.ecoproject.domain.usecases.point.IsPointFavoriteParams
import com.example.ecoproject.domain.usecases.point.IsPointFavoriteUseCase
import com.example.ecoproject.domain.usecases.point.RemoveFavoritePointParams
import com.example.ecoproject.domain.usecases.point.RemoveFavoritePointUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import javax.inject.Inject

class PointViewModel @Inject constructor(
    getPointUseCase: GetPointUseCase,
    isPointFavoriteUseCase: IsPointFavoriteUseCase,
    private val addFavoritePointUseCase: AddFavoritePointUseCase,
    private val removeFavoritePointUseCase: RemoveFavoritePointUseCase,
) : BaseViewModel() {
    val pointID = MutableSharedFlow<String>(1)

    val point = pointID.flatMapConcat {
        combine(
            getPointUseCase(GetPointParams(it)),
            isPointFavoriteUseCase(IsPointFavoriteParams(it)),
        ) { point, isFavorite -> point to isFavorite }
    }.shareWhileSubscribed()

    fun addToFavorite() = pointID.take(1).flatMapConcat {
        addFavoritePointUseCase(AddFavoriteParams(it))
    }.onEach {
        pointID.emit(it.id)
    }

    fun removeFromFavorite() = pointID.take(1).flatMapConcat {
        removeFavoritePointUseCase(RemoveFavoritePointParams(it))
    }.onEach {
        pointID.emit(it.id)
    }
}