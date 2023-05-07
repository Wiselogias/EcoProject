package com.example.ecoproject.data.firebase.datasource

import android.graphics.Bitmap
import com.example.ecoproject.data.firebase.utils.FirestoreUtils.asCoroutine
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream
import java.util.UUID
import javax.inject.Inject

class ImageDataSource @Inject constructor(
    private val store: StorageReference
) {
    suspend fun upload(bitmap: Bitmap): String {
        val imageRef = store.child(UUID.randomUUID().toString())
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, baos)
        val data = baos.toByteArray()

        val snapshot = imageRef.putBytes(data).asCoroutine()

        return snapshot.storage.path
    }
}