package com.example.ecoproject.data.utils

import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

object CoordUtils {
    private const val EARTH_RADIUS_KM = 6371
    fun distance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {

        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val lat11 = Math.toRadians(lat1)
        val lat21 = Math.toRadians(lat2)
        val a = sin(dLat / 2) * sin(dLat / 2) +
                sin(dLon / 2) * sin(dLon / 2) * cos(lat11) * cos(lat21)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return EARTH_RADIUS_KM * c
    }
}