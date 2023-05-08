package com.example.ecoproject.data.remote

import com.google.gson.annotations.SerializedName

data class AnalyseResponse(
    @SerializedName("type")
    val type: String,
    @SerializedName("nearestPoint")
    val point: Point?,
)

data class Point(
    @SerializedName("x")
    val x: Double,
    @SerializedName("y")
    val y: Double,
)