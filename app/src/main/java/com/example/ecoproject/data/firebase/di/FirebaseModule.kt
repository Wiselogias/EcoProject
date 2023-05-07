package com.example.ecoproject.data.firebase.di

import com.example.ecoproject.data.di.DataScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
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

    @Provides
    @DataScope
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth

    @Provides
    @DataScope
    fun provideImagesRef(): StorageReference = Firebase.storage.getReference("articles")
}