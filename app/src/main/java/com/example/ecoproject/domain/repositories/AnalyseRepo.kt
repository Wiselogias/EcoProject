package com.example.ecoproject.domain.repositories

import android.graphics.Bitmap
import com.example.ecoproject.domain.entities.AnalyseEntity

interface AnalyseRepo {
    suspend fun analyseImage(bitmap: Bitmap, x: Double, y: Double): AnalyseEntity
}