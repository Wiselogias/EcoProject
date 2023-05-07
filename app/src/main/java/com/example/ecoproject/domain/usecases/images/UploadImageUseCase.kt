package com.example.ecoproject.domain.usecases.images

import android.graphics.Bitmap
import com.example.ecoproject.domain.repositories.ImageRepo
import com.example.ecoproject.domain.usecases.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.InputStream
import javax.inject.Inject

class UploadImageUseCase @Inject constructor(
    private val imageRepo: ImageRepo
) : UseCase<InputStream?, ImageUploadResult> {
    override fun invoke(input: InputStream?): Flow<ImageUploadResult> = flow {
        try {
            if (input == null) throw RuntimeException("input stream can not be null")
            val (bitmap, ref) = imageRepo.upload(input)
            emit(ImageUploadResult.Success(bitmap, ref))
        } catch (e: Exception) {
            emit(ImageUploadResult.Failed(e))
        }
    }.flowOn(Dispatchers.IO)
}

sealed class ImageUploadResult {
    data class Success(val bitmap: Bitmap, val ref: String) : ImageUploadResult()
    data class Failed(val t: Throwable) : ImageUploadResult()
}