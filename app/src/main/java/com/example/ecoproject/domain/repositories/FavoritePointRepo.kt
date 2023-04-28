package com.example.ecoproject.domain.repositories

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.ecoproject.domain.entities.PointEntity
import kotlinx.coroutines.flow.Flow

interface FavoritePointRepo {
    suspend fun addPoint(point: PointEntity): PointEntity
    suspend fun getPoint(id: String): PointEntity
    suspend fun getPoints(): List<PointEntity>
    suspend fun deletePoint(id: String): PointEntity
    fun getPointsPaged(pagingConfig: PagingConfig): Flow<PagingData<PointEntity>>
}