package com.example.ecoproject.domain.usecases.auth

import com.example.ecoproject.domain.repositories.AuthRepo
import com.example.ecoproject.domain.repositories.AuthState
import com.example.ecoproject.domain.usecases.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAuthStateUseCase @Inject constructor(
    private val authRepo: AuthRepo
) : UseCase<Unit, AuthState> {
    override fun invoke(input: Unit): Flow<AuthState> = authRepo.authStateFlow()
}