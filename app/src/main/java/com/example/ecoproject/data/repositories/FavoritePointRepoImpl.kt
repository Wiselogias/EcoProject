package com.example.ecoproject.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.ecoproject.data.db.dao.FavoritePointDao
import com.example.ecoproject.data.db.mappers.FavoritePointMapper
import com.example.ecoproject.domain.entities.PointEntity
import com.example.ecoproject.domain.repositories.FavoritePointRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoritePointRepoImpl @Inject constructor(
    private val favoritePointDao: FavoritePointDao
) : FavoritePointRepo {
    override suspend fun addPoint(point: PointEntity): PointEntity {
        favoritePointDao.upsert(FavoritePointMapper.fromDomainEntityToRoomEntity(point))
        return point
    }

    override suspend fun getPoint(id: String): PointEntity = FavoritePointMapper
        .fromRoomEntityToDomainEntity(favoritePointDao.getPoint(id))

    override suspend fun getPoints(): List<PointEntity> =
        favoritePointDao.getPoints().map { FavoritePointMapper.fromRoomEntityToDomainEntity(it) }

    override suspend fun deletePoint(id: String): PointEntity {
        val deleted = favoritePointDao.getPoint(id)
        favoritePointDao.delete(deleted)
        return FavoritePointMapper.fromRoomEntityToDomainEntity(deleted)
    }

    override fun getPointsPaged(pagingConfig: PagingConfig): Flow<PagingData<PointEntity>> =
        Pager(pagingConfig) {
            favoritePointDao.getPointsPaged()
        }.flow.map { pagingData ->
            pagingData.map {
                FavoritePointMapper.fromRoomEntityToDomainEntity(it)
            }
        }
}