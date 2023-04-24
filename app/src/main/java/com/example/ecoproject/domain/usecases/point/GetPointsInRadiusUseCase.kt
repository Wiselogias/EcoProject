package com.example.ecoproject.domain.usecases.point

import com.example.ecoproject.domain.entities.PointEntity
import com.example.ecoproject.domain.repositories.PointRepo
import com.example.ecoproject.domain.usecases.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPointInRadiusUseCase @Inject constructor(
    private val pointRepo: PointRepo
) : UseCase<GetPointInRadiusParams, List<PointEntity>> {
    override fun invoke(input: GetPointInRadiusParams): Flow<List<PointEntity>> = flow {
        emit(
            pointRepo.getPointsWithinRange(
                input.lat,
                input.lon,
                input.rad
            )
        )
    }
}

data class GetPointInRadiusParams(
    val lat: Double,
    val lon: Double,
    val rad: Double
)