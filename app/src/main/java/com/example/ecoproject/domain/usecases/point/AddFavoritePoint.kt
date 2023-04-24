package com.example.ecoproject.domain.usecases.point

import com.example.ecoproject.domain.entities.PointEntity
import com.example.ecoproject.domain.repositories.FavoritePointRepo
import com.example.ecoproject.domain.usecases.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddFavoritePoint @Inject constructor(
    private val favoritePointRepo: FavoritePointRepo,
    private val pointRepo: FavoritePointRepo
) : UseCase<AddFavoriteParams, PointEntity> {
    override fun invoke(input: AddFavoriteParams): Flow<PointEntity> = flow {
        emit(favoritePointRepo.addPoint(pointRepo.getPoint(input.id)))
    }
}

data class AddFavoriteParams(
    val id: String
)