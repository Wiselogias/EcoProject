package com.example.ecoproject.ui.mapfragment

import com.example.ecoproject.common.mvvm.BaseViewModel
import com.example.ecoproject.domain.usecases.point.GetPointInRadiusParams
import com.example.ecoproject.domain.usecases.point.GetPointInRadiusUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import javax.inject.Inject

class MapViewModel @Inject constructor(
    getPointsInRadiusUseCase: GetPointInRadiusUseCase,
) : BaseViewModel() {
    val lat = MutableSharedFlow<Double>(replay = 1)
    val lon = MutableSharedFlow<Double>(replay = 1)
    val radius = MutableSharedFlow<Double>(replay = 1)

    val points = combine(lat, lon, radius) { lat, lon, radius ->
        GetPointInRadiusParams(lat, lon, radius)
    }.flatMapConcat {
        getPointsInRadiusUseCase(it)
    }.shareWhileSubscribed()
}