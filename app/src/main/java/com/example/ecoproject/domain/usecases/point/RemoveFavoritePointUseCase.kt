package com.example.ecoproject.domain.usecases.point

import com.example.ecoproject.domain.entities.PointEntity
import com.example.ecoproject.domain.repositories.FavoritePointRepo
import com.example.ecoproject.domain.usecases.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import javax.inject.Inject

class RemoveFavoritePointUseCase @Inject constructor(
    private val favoritePointRepo: FavoritePointRepo
) : UseCase<RemoveFavoritePointParams, PointEntity> {
    override fun invoke(input: RemoveFavoritePointParams): Flow<PointEntity> = flow {
        emit(favoritePointRepo.deletePoint(input.id))
    }
}

data class RemoveFavoritePointParams(
    val id: String
)