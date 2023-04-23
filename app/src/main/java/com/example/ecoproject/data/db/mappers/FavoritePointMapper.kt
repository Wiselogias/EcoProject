package com.example.ecoproject.data.db.mappers

import com.example.ecoproject.data.db.entities.FavoritePointRoomEntity
import com.example.ecoproject.domain.entities.PointEntity

object FavoritePointMapper {
    fun fromRoomEntityToDomainEntity(favoritePointRoomEntity: FavoritePointRoomEntity): PointEntity = PointEntity(
        id = favoritePointRoomEntity.id,
        address = favoritePointRoomEntity.address,
        lat = favoritePointRoomEntity.lat,
        lon = favoritePointRoomEntity.lon
    )

    fun fromDomainEntityToRoomEntity(favoritePointDomainEntity: PointEntity): FavoritePointRoomEntity = FavoritePointRoomEntity(
        id = favoritePointDomainEntity.id,
        address = favoritePointDomainEntity.address,
        lat = favoritePointDomainEntity.lat,
        lon = favoritePointDomainEntity.lon
    )
}