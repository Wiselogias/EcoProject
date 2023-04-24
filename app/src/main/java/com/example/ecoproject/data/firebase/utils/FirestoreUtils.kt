package com.example.ecoproject.data.firebase.utils

import com.google.android.gms.tasks.Task
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object FirestoreUtils {
    suspend fun <T> Task<T>.asCoroutine() =
        suspendCoroutine<T> { continuation ->
            addOnSuccessListener {
                continuation.resume(it)
            }
            addOnFailureListener {
                continuation.resumeWithException(it)
            }

        }
}