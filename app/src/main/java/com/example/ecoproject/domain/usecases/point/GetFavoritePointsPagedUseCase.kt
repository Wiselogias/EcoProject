package com.example.ecoproject.domain.usecases.point

import androidx.paging.PagingSource
import com.example.ecoproject.domain.entities.PointEntity
import com.example.ecoproject.domain.repositories.FavoritePointRepo
import com.example.ecoproject.domain.usecases.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetFavoritePointsPagedUseCase @Inject constructor(
    private val favoritePointRepo: FavoritePointRepo
) : UseCase<Unit, PagingSource<Int, PointEntity>> {
    override fun invoke(input: Unit): Flow<PagingSource<Int, PointEntity>> =
        flowOf(favoritePointRepo.getPointsPaged())
}