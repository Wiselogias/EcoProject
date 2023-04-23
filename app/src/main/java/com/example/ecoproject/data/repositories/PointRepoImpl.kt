package com.example.ecoproject.data.repositories

import com.example.ecoproject.data.db.dao.PointDao
import com.example.ecoproject.domain.entities.PointEntity
import com.example.ecoproject.domain.repositories.PointRepo
import javax.inject.Inject

class PointRepoImpl @Inject constructor(
    private val pointDao: PointDao
) : PointRepo {
    override suspend fun createPoint(point: PointEntity): PointEntity {
        TODO("Not yet implemented")
    }

    override suspend fun getPoint(id: String): PointEntity {
        TODO("Not yet implemented")
    }

    override suspend fun getPointsWithinRange(lat: Double, lon: Double): List<PointEntity> {
        TODO("Not yet implemented")
    }
}