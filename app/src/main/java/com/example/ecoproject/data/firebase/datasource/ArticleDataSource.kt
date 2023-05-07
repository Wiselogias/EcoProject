package com.example.ecoproject.data.firebase.datasource

import com.example.ecoproject.data.firebase.entities.ArticleFirebaseEntity
import com.example.ecoproject.data.firebase.utils.FirestoreUtils.asCoroutine
import com.example.ecoproject.domain.entities.ArticleEntity
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import javax.inject.Inject
import javax.inject.Named

class ArticleDataSource @Inject constructor(
    @Named("articlesCollectionReference") private val collection: CollectionReference
) {
    suspend fun saveArticle(articleEntity: ArticleEntity): ArticleEntity {
        val ref = collection.add(
            hashMapOf(
                "title" to articleEntity.title,
                "text" to articleEntity.text,
                "imageReference" to articleEntity.imageReference,
                "time" to articleEntity.time.millis,
                "author" to articleEntity.author
            )
        ).asCoroutine()

        return ArticleEntity(
            ref.id,
            articleEntity.title,
            articleEntity.text,
            articleEntity.imageReference,
            articleEntity.time,
            articleEntity.author
        )
    }

    suspend fun getArticlesPaged(
        limit: Int, snapshot: DocumentSnapshot?
    ): List<ArticleFirebaseEntity> =
        if (snapshot != null) collection.orderBy("time", Query.Direction.DESCENDING)
            .startAfter(snapshot)
            .limit(limit.toLong())
            .get().asCoroutine().map {
                ArticleFirebaseEntity(it)
            }.filterNotNull()
        else collection.orderBy("time", Query.Direction.DESCENDING)
            .limit(limit.toLong())
            .get().asCoroutine().map {
                ArticleFirebaseEntity(it)
            }.filterNotNull()
}