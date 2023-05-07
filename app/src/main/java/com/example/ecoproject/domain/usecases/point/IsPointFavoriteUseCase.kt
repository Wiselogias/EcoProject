package com.example.ecoproject.domain.usecases.point

import com.example.ecoproject.domain.repositories.FavoritePointRepo
import com.example.ecoproject.domain.usecases.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class IsPointFavoriteUseCase @Inject constructor(
    private val pointRepo: FavoritePointRepo
) : UseCase<IsPointFavoriteParams, Boolean> {
    override fun invoke(input: IsPointFavoriteParams): Flow<Boolean> = flow {
        emit(pointRepo.getPoints().any { it.id == input.id })
    }
}

data class IsPointFavoriteParams(
    val id: String
)