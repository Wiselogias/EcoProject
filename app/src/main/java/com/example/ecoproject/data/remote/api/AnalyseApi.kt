package com.example.ecoproject.data.remote.api

import com.example.ecoproject.data.remote.AnalyseResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AnalyseApi {
    @Multipart
    @POST("api/v1/recognize")
    suspend fun analyse(
        @Part image: MultipartBody.Part,
        @Part coordinates: MultipartBody.Part,
    ): AnalyseResponse
}