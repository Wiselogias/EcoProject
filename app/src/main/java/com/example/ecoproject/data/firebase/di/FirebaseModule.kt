package com.example.ecoproject.data.firebase.di

import com.example.ecoproject.data.di.DataScope
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class FirebaseModule {
    @Named("articlesCollectionReference")
    @Provides
    @DataScope
    fun provideArticleCollectionReference(): CollectionReference =
        Firebase.firestore.collection("articles")

    @Named("pointsCollectionReference")
    @Provides
    @DataScope
    fun providePointCollectionReference(): CollectionReference =
        Firebase.firestore.collection("points")
}