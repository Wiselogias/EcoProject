package com.example.ecoproject.data.firebase.utils

import com.google.android.gms.tasks.Task
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object FirestoreUtils {
    suspend fun <T> Task<T>.asCoroutine() =
        suspendCoroutine<T> { continuation ->
            var continued = false
            addOnSuccessListener {
                if (continued) return@addOnSuccessListener
                continued = true
                continuation.resume(it)
            }
            addOnFailureListener {
                if (continued) return@addOnFailureListener
                continued = true
                continuation.resumeWithException(it)
            }
//            addOnCompleteListener {
//                if (continued) return@addOnCompleteListener
//                continued = true
//                continuation.resume(it.result)
//            }
        }
}