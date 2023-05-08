package com.example.ecoproject.data.repositories

import android.graphics.Bitmap
import com.example.ecoproject.domain.entities.AnalyseEntity
import com.example.ecoproject.domain.repositories.AnalyseRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TestAnalyseRepoImpl @Inject constructor() : AnalyseRepo {
    private val types = listOf("plastic", "paper", "metal", "battery")

    override suspend fun analyseImage(bitmap: Bitmap, x: Double, y: Double): AnalyseEntity =
        withContext(Dispatchers.IO) {
            delay(4000)
            return@withContext AnalyseEntity(types.random())
        }
}