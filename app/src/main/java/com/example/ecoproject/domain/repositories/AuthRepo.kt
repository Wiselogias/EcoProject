package com.example.ecoproject.domain.repositories

import android.app.Activity
import com.example.ecoproject.domain.entities.UserEntity
import kotlinx.coroutines.flow.Flow

interface AuthRepo {
    fun authStateFlow(): Flow<AuthState>

    fun verificationStateFlow(): Flow<VerificationState>

    suspend fun sendSMS(phone: String, activity: Activity)

    suspend fun verifyCode(input: String)

    suspend fun signOut()
}

sealed class AuthState {
    object NotAuthed : AuthState()
    data class Authed(
        val user: UserEntity
    ) : AuthState()

    data class Error(
        val t: Throwable
    ) : AuthState()
}

sealed class VerificationState {
    object NotSent : VerificationState()
    data class Sent(
        val verificationId: String
    ) : VerificationState()
}