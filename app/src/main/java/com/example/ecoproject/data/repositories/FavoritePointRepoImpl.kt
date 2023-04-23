package com.example.ecoproject.data.repositories

import androidx.paging.PagingSource
import com.example.ecoproject.data.db.dao.FavoritePointDao
import com.example.ecoproject.domain.entities.PointEntity
import com.example.ecoproject.domain.repositories.FavoritePointRepo
import javax.inject.Inject

class FavoritePointRepoImpl @Inject constructor(
    private val favoritePointDao: FavoritePointDao
) : FavoritePointRepo {
    override suspend fun addPoint(point: PointEntity): PointEntity {
        TODO("Not yet implemented")
    }

    override suspend fun getPoint(id: String): PointEntity {
        TODO("Not yet implemented")
    }

    override suspend fun getPoints(): List<PointEntity> {
        TODO("Not yet implemented")
    }

    override fun getPointsPaged(): PagingSource<Int, PointEntity> {
        TODO("Not yet implemented")
    }
}