package com.example.ecoproject.data.firebase.mappers

import com.example.ecoproject.data.firebase.entities.PointFirebaseEntity
import com.example.ecoproject.domain.entities.PointEntity

object PointFirebaseMapper {
    fun fromFirebaseEntityToDomainEntity(pointFirebaseEntity: PointFirebaseEntity): PointEntity =
        PointEntity(
            id = pointFirebaseEntity.id,
            address = pointFirebaseEntity.address,
            lat = pointFirebaseEntity.lat,
            lon = pointFirebaseEntity.lon
        )

    fun fromDomainEntityToFirebaseEntity(pointDomainEntity: PointEntity): PointFirebaseEntity =
        PointFirebaseEntity(
            id = pointDomainEntity.id,
            address = pointDomainEntity.address,
            lat = pointDomainEntity.lat,
            lon = pointDomainEntity.lon
        )
}