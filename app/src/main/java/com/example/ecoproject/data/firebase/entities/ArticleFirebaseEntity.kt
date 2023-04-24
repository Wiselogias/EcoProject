package com.example.ecoproject.data.firebase.entities

import com.google.firebase.firestore.DocumentSnapshot

data class ArticleFirebaseEntity(
    val documentSnapshot: DocumentSnapshot
) {
    val id: String
        get() = documentSnapshot.id
    val title: String
        get() = documentSnapshot.data?.get("title") as String
    val text: String
        get() = documentSnapshot.data?.get("text") as String
    val imageReference: String
        get() = documentSnapshot.data?.get("imageReference") as String
    val time: Long
        get() = documentSnapshot.data?.get("time") as Long

    val author: String
        get() = documentSnapshot.data?.get("author") as String
}