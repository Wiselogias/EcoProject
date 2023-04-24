package com.example.ecoproject.data.db.mappers

import com.example.ecoproject.data.db.entities.FavoriteArticleRoomEntity
import com.example.ecoproject.domain.entities.ArticleEntity

object FavoriteArticleMapper {

    fun fromRoomEntityToDomainEntity(favoriteArticleRoomEntity: FavoriteArticleRoomEntity): ArticleEntity = ArticleEntity(
        id = favoriteArticleRoomEntity.id,
        title = favoriteArticleRoomEntity.title,
        time = favoriteArticleRoomEntity.time,
        text = favoriteArticleRoomEntity.text,
        imageReference = favoriteArticleRoomEntity.imageReference,
        author = favoriteArticleRoomEntity.author
    )

    fun fromDomainEntityToRoomEntity(favoriteArticleDomainEntity: ArticleEntity): FavoriteArticleRoomEntity = FavoriteArticleRoomEntity(
        id = favoriteArticleDomainEntity.id,
        title = favoriteArticleDomainEntity.title,
        time = favoriteArticleDomainEntity.time,
        text = favoriteArticleDomainEntity.text,
        imageReference = favoriteArticleDomainEntity.imageReference,
        author = favoriteArticleDomainEntity.author
    )
}