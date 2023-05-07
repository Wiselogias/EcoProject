package com.example.ecoproject.domain.repositories

import android.graphics.Bitmap
import java.io.InputStream

interface ImageRepo {
    suspend fun upload(inputStream: InputStream): Pair<Bitmap, String>
}