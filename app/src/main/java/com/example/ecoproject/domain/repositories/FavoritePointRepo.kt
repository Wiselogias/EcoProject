package com.example.ecoproject.domain.repositories

import androidx.paging.PagingSource
import com.example.ecoproject.domain.entities.PointEntity

interface FavoritePointRepo {
    suspend fun addPoint(point: PointEntity): PointEntity
    suspend fun getPoint(id: String): PointEntity
    suspend fun getPoints(): List<PointEntity>
    suspend fun deletePoint(id: String): PointEntity
    fun getPointsPaged(): PagingSource<Int, PointEntity>
}