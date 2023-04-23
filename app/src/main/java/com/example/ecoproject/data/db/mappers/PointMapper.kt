package com.example.ecoproject.data.db.mappers

import com.example.ecoproject.data.db.entities.PointRoomEntity
import com.example.ecoproject.domain.entities.PointEntity

object PointMapper {
    fun fromRoomEntityToDomainEntity(pointRoomEntity: PointRoomEntity): PointEntity = PointEntity(
        id = pointRoomEntity.id,
        address = pointRoomEntity.address,
        lat = pointRoomEntity.lat,
        lon = pointRoomEntity.lon
    )

    fun fromDomainEntityToRoomEntity(pointDomainEntity: PointEntity): PointRoomEntity = PointRoomEntity(
        id = pointDomainEntity.id,
        address = pointDomainEntity.address,
        lat = pointDomainEntity.lat,
        lon = pointDomainEntity.lon
    )
}