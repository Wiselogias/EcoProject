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

class GetImageBitmapUseCase @Inject constructor(
    private val imageRepo: ImageRepo
) : UseCase<InputStream?, GetImageBitmapResult> {
    override fun invoke(input: InputStream?): Flow<GetImageBitmapResult> = flow {
        try {
            if (input == null) throw RuntimeException("Image input stream can not be null")
            emit(GetImageBitmapResult.Success(imageRepo.bitmap(input)))
        } catch (t: Throwable) {
            emit(GetImageBitmapResult.Failed(t))
        }
    }.flowOn(Dispatchers.IO)
}

sealed class GetImageBitmapResult {
    data class Success(val bitmap: Bitmap) : GetImageBitmapResult()
    data class Failed(val t: Throwable) : GetImageBitmapResult()
}
