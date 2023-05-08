package com.example.ecoproject.data.repositories

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import com.example.ecoproject.data.firebase.datasource.ImageDataSource
import com.example.ecoproject.domain.repositories.ImageRepo
import java.io.InputStream
import javax.inject.Inject
import kotlin.math.min

class ImageRepoImpl @Inject constructor(
    private val imageDataSource: ImageDataSource
) : ImageRepo {
    override suspend fun upload(inputStream: InputStream): Pair<Bitmap, String> {
        val bitmap = BitmapFactory.decodeStream(inputStream).scaleByMaxSize(750, 750)
        val ref = imageDataSource.upload(bitmap)
        return bitmap to ref
    }

    override suspend fun bitmap(inputStream: InputStream): Bitmap =
        BitmapFactory.decodeStream(inputStream).scaleByMaxSize(750, 750)

    private fun Bitmap.scaleByMaxSize(maxWidth: Int, maxHeight: Int): Bitmap {
        val scaleFactor = min(maxWidth.toFloat() / width, maxHeight.toFloat() / height)
        val matrix = Matrix()
        matrix.postScale(scaleFactor, scaleFactor)
        return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true).also {
            recycle()
        }
    }
}