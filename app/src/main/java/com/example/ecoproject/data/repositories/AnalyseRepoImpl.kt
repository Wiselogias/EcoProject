package com.example.ecoproject.data.repositories

import android.graphics.Bitmap
import com.example.ecoproject.data.remote.api.AnalyseApi
import com.example.ecoproject.domain.entities.AnalyseEntity
import com.example.ecoproject.domain.repositories.AnalyseRepo
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.util.UUID
import javax.inject.Inject

class AnalyseRepoImpl @Inject constructor(
    private val analyseApi: AnalyseApi,
    private val gson: Gson,
) : AnalyseRepo {
    override suspend fun analyseImage(bitmap: Bitmap, x: Double, y: Double): AnalyseEntity =
        withContext(Dispatchers.IO) {
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos)
            bitmap.recycle()

            val imagePart = MultipartBody.Part.createFormData(
                "image",
                "${UUID.randomUUID()}.png",
                bos.toByteArray().toRequestBody(
                    "image/png".toMediaTypeOrNull()
                )
            )

            val coordsPart = MultipartBody.Part.createFormData(
                "coordinates",
                "${UUID.randomUUID()}.json",
                gson.toJson(Coordinates(x, y)).toRequestBody(
                    "application/json".toMediaTypeOrNull()
                )
            )

            val response = analyseApi.analyse(imagePart, coordsPart)

            return@withContext AnalyseEntity(response.type)
        }


    internal data class Coordinates(
        @SerializedName("x")
        val x: Double,
        @SerializedName("y")
        val y: Double,
    )
}