package com.example.ecoproject.data.repositories

import com.example.ecoproject.data.db.dao.PointDao
import com.example.ecoproject.data.db.entities.PointRoomEntity
import com.example.ecoproject.data.db.mappers.PointMapper
import com.example.ecoproject.data.firebase.datasource.PointDataSource
import com.example.ecoproject.data.utils.CoordUtils
import com.example.ecoproject.domain.entities.PointEntity
import com.example.ecoproject.domain.repositories.PointRepo
import javax.inject.Inject

class PointRepoImpl @Inject constructor(
    private val pointDao: PointDao,
    private val firebaseDataSource: PointDataSource
) : PointRepo {
    override suspend fun createPoint(point: PointEntity): PointEntity {
        pointDao.upsert(PointMapper.fromDomainEntityToRoomEntity(point))
        return point
    }

    override suspend fun getPoint(id: String): PointEntity =
        PointMapper.fromRoomEntityToDomainEntity(pointDao.getPoint(id))


    override suspend fun getPointsWithinRange(
        lat: Double,
        lon: Double,
        rad: Double
    ): List<PointEntity> =
        pointDao
            .getPoints()
            .filter { CoordUtils.distance(it.lat, it.lon, lat, lon) <= rad }
            .map { PointMapper.fromRoomEntityToDomainEntity(it) }

    override suspend fun invalidate() {
        firebaseDataSource.getPoints().forEach {
            pointDao.upsert(
                PointRoomEntity(
                    id = it.id,
                    address = it.address,
                    lat = it.lat,
                    lon = it.lon
                )
            )
        }
    }
}