package com.example.ecoproject.domain.usecases.auth

import android.app.Activity
import com.example.ecoproject.domain.repositories.AuthRepo
import com.example.ecoproject.domain.repositories.AuthState
import com.example.ecoproject.domain.usecases.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SendSMSUseCase @Inject constructor(
    private val authRepo: AuthRepo
) : UseCase<SendSMSParams, AuthState> {
    override fun invoke(input: SendSMSParams): Flow<AuthState> = flow {
        authRepo.sendSMS(input.phone, input.activity)
        emit(Unit)
    }.flatMapConcat {
        authRepo.authStateFlow()
    }
}

data class SendSMSParams(
    val phone: String,
    val activity: Activity
)