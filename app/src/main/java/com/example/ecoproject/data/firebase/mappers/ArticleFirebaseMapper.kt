package com.example.ecoproject.data.firebase.mappers

import com.example.ecoproject.data.firebase.entities.ArticleFirebaseEntity
import com.example.ecoproject.domain.entities.ArticleEntity
import org.joda.time.DateTime

object ArticleFirebaseMapper {

    fun fromFirebaseEntityToDomainEntity(articleFirebaseEntity: ArticleFirebaseEntity): ArticleEntity =
        ArticleEntity(
            id = articleFirebaseEntity.id,
            title = articleFirebaseEntity.title,
            time = DateTime(articleFirebaseEntity.time),
            text = articleFirebaseEntity.text,
            imageReference = articleFirebaseEntity.imageReference
        )
}