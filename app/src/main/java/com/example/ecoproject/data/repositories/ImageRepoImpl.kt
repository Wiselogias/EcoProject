package com.example.ecoproject.data.repositories

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.ecoproject.data.firebase.datasource.ImageDataSource
import com.example.ecoproject.domain.repositories.ImageRepo
import java.io.InputStream
import javax.inject.Inject

class ImageRepoImpl @Inject constructor(
    private val imageDataSource: ImageDataSource
) : ImageRepo {
    override suspend fun upload(inputStream: InputStream): Pair<Bitmap, String> {
        val bitmap = BitmapFactory.decodeStream(inputStream)
        val ref = imageDataSource.upload(bitmap)
        return bitmap to ref
    }
}