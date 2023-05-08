package com.example.ecoproject.domain.usecases.analyse

import android.graphics.Bitmap
import com.example.ecoproject.domain.entities.AnalyseEntity
import com.example.ecoproject.domain.repositories.AnalyseRepo
import com.example.ecoproject.domain.usecases.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AnalyseImageUseCase @Inject constructor(
    private val analyseRepo: AnalyseRepo
) : UseCase<AnalyseImageParams, AnalysesImageResult> {
    override fun invoke(input: AnalyseImageParams): Flow<AnalysesImageResult> = flow {
        try {
            val result = analyseRepo.analyseImage(input.bitmap, input.x, input.y)
            emit(AnalysesImageResult.Success(result))
        } catch (t: Throwable) {
            emit(AnalysesImageResult.Failed(t))
        }
    }
}


data class AnalyseImageParams(
    val bitmap: Bitmap,
    val x: Double,
    val y: Double,
)

sealed class AnalysesImageResult {
    data class Success(val analyse: AnalyseEntity) : AnalysesImageResult()
    data class Failed(val t: Throwable) : AnalysesImageResult()
}