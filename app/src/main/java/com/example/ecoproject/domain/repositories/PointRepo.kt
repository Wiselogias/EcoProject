package com.example.ecoproject.domain.repositories

import com.example.ecoproject.domain.entities.PointEntity

interface PointRepo {
    suspend fun createPoint(point: PointEntity): PointEntity
    suspend fun getPoint(id: String): PointEntity
    suspend fun getPointsWithinRange(lat: Double, lon: Double): List<PointEntity>
}