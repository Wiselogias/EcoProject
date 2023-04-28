package com.example.ecoproject.domain.usecases.point

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.ecoproject.domain.entities.PointEntity
import com.example.ecoproject.domain.repositories.FavoritePointRepo
import com.example.ecoproject.domain.usecases.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoritePointsPagedUseCase @Inject constructor(
    private val favoritePointRepo: FavoritePointRepo
) : UseCase<PagingConfig, PagingData<PointEntity>> {
    override fun invoke(input: PagingConfig): Flow<PagingData<PointEntity>> =
        favoritePointRepo.getPointsPaged(input)

}