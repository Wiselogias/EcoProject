package com.example.ecoproject.data.repositories

import com.example.ecoproject.domain.entities.UserEntity
import com.example.ecoproject.domain.repositories.AuthRepo
import com.example.ecoproject.domain.repositories.AuthState
import com.example.ecoproject.domain.repositories.VerificationState
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthRepoImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepo {
    private val authState = MutableStateFlow<AuthState>(AuthState.NotAuthed)
    private val verificationState = MutableStateFlow<VerificationState>(VerificationState.NotSent)

    override fun authStateFlow(): Flow<AuthState> = authState

    override fun verificationStateFlow(): Flow<VerificationState> = verificationState

    override suspend fun sendSMS(phone: String) {
        val callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                signWithCredentials(p0)
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                runBlocking {
                    authState.emit(AuthState.Error(p0))
                }
            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(p0, p1)
                runBlocking { verificationState.emit(VerificationState.Sent(p0)) }
            }
        }
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phone)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setCallbacks(callback)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override suspend fun verifyCode(input: String) {
        val verificationState = verificationState.first()
        if (verificationState !is VerificationState.Sent)
            throw RuntimeException("code is not requested")
        val credentials = PhoneAuthProvider.getCredential(verificationState.verificationId, input)
        signWithCredentials(credentials)
    }

    override suspend fun signOut() {
        auth.signOut()
        authState.emit(AuthState.NotAuthed)
    }

    private fun signWithCredentials(credentials: PhoneAuthCredential) {
        auth.signInWithCredential(credentials).addOnSuccessListener {
            runBlocking {
                authState.emit(AuthState.Authed(UserEntity(it.user!!.uid, it.user!!.phoneNumber!!)))
            }
        }.addOnFailureListener {
            runBlocking {
                authState.emit(AuthState.Error(it))
            }
        }
    }

}