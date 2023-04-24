package com.example.ecoproject.domain.usecases.point

import com.example.ecoproject.domain.entities.PointEntity
import com.example.ecoproject.domain.repositories.FavoritePointRepo
import com.example.ecoproject.domain.usecases.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFavoritePointUseCase @Inject constructor(
    private val favoritePointRepo: FavoritePointRepo
) : UseCase<GetFavoritePointParam, PointEntity> {
    override fun invoke(input: GetFavoritePointParam): Flow<PointEntity> = flow {
        emit(favoritePointRepo.getPoint(input.id))
    }
}

data class GetFavoritePointParam(
    val id: String
)