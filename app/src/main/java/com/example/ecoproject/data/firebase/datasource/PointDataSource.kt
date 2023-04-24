package com.example.ecoproject.data.firebase.datasource

import com.example.ecoproject.data.firebase.entities.PointFirebaseEntity
import com.example.ecoproject.data.firebase.utils.FirestoreUtils.asCoroutine
import com.google.firebase.firestore.CollectionReference
import javax.inject.Inject
import javax.inject.Named

class PointDataSource @Inject constructor(
    @Named("pointsCollectionReference")
    private val collectionReference: CollectionReference
) {
    suspend fun getPoints(): List<PointFirebaseEntity> = collectionReference
        .get()
        .asCoroutine()
        .map {
            PointFirebaseEntity(
                it.id,
                it.data["address"] as String,
                it.data["lat"] as Double,
                it.data["lon"] as Double
            )
        }
}