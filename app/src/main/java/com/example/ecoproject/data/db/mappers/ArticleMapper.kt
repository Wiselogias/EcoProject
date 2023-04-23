package com.example.ecoproject.data.db.mappers

import com.example.ecoproject.data.db.entities.ArticleRoomEntity
import com.example.ecoproject.domain.entities.ArticleEntity

object ArticleMapper {

    fun fromRoomEntityToDomainEntity(articleRoomEntity: ArticleRoomEntity): ArticleEntity = ArticleEntity(
        id = articleRoomEntity.id,
        title = articleRoomEntity.title,
        time = articleRoomEntity.time,
        text = articleRoomEntity.text,
        imageReference = articleRoomEntity.imageReference)

    fun fromDomainEntityToRoomEntity(articleDomainEntity: ArticleEntity): ArticleRoomEntity = ArticleRoomEntity(
        id = articleDomainEntity.id,
        title = articleDomainEntity.title,
        time = articleDomainEntity.time,
        text = articleDomainEntity.text,
        imageReference = articleDomainEntity.imageReference)
}