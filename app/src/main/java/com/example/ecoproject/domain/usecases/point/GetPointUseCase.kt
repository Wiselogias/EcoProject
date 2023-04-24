package com.example.ecoproject.domain.usecases.point

import com.example.ecoproject.domain.entities.PointEntity
import com.example.ecoproject.domain.repositories.FavoritePointRepo
import com.example.ecoproject.domain.usecases.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPointUseCase @Inject constructor(
    private val pointRepo: FavoritePointRepo
) : UseCase<GetPointParams, PointEntity> {
    override fun invoke(input: GetPointParams): Flow<PointEntity> = flow {
        emit(pointRepo.getPoint(input.id))
    }
}

data class GetPointParams(
    val id: String
)