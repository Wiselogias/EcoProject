package com.example.ecoproject.domain.usecases.point

import com.example.ecoproject.domain.repositories.PointRepo
import com.example.ecoproject.domain.usecases.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class InvalidatePointsUseCase @Inject constructor(
    private val pointRepo: PointRepo
) : UseCase<Unit, Unit> {
    override fun invoke(input: Unit): Flow<Unit> = flow {
        pointRepo.invalidate()
        emit(Unit)
    }.flowOn(Dispatchers.IO)

}