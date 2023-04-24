package com.example.ecoproject.domain.usecases.auth

import com.example.ecoproject.domain.repositories.AuthRepo
import com.example.ecoproject.domain.repositories.VerificationState
import com.example.ecoproject.domain.usecases.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VerificationStateUseCase @Inject constructor(
    private val authRepo: AuthRepo
) : UseCase<Unit, VerificationState> {
    override fun invoke(input: Unit): Flow<VerificationState> = authRepo.verificationStateFlow()
}