package com.example.ecoproject.ui.mapfragment

import androidx.lifecycle.viewModelScope
import com.example.ecoproject.common.mvvm.BaseViewModel
import com.example.ecoproject.domain.usecases.point.GetPointInRadiusParams
import com.example.ecoproject.domain.usecases.point.GetPointInRadiusUseCase
import com.example.ecoproject.domain.usecases.point.InvalidatePointsUseCase
import com.example.ecoproject.ui.utils.UIUtils.collectIn
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.flatMapConcat
import javax.inject.Inject

class MapViewModel @Inject constructor(
    getPointsInRadiusUseCase: GetPointInRadiusUseCase,
    invalidatePointsUseCase: InvalidatePointsUseCase,
) : BaseViewModel() {
    init {
        invalidatePointsUseCase(Unit).collectIn(viewModelScope) { }
    }

    val lat = MutableSharedFlow<Double>(replay = 1)
    val lon = MutableSharedFlow<Double>(replay = 1)
    val radius = MutableSharedFlow<Double>(replay = 1)

    val points = combine(
        lat.distinctUntilChanged(),
        lon.distinctUntilChanged(),
        radius.distinctUntilChanged()
    ) { lat, lon, radius ->
        GetPointInRadiusParams(lat, lon, radius)
    }.flatMapConcat {
        getPointsInRadiusUseCase(it)
    }.filterNot { it.isEmpty() }.shareWhileSubscribed()
}