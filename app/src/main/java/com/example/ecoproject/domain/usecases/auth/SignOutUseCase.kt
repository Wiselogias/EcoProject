package com.example.ecoproject.domain.usecases.auth

import com.example.ecoproject.domain.repositories.AuthRepo
import com.example.ecoproject.domain.usecases.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val authRepo: AuthRepo
) : UseCase<Unit, Unit> {
    override fun invoke(input: Unit): Flow<Unit> = flow {
        authRepo.signOut()
        emit(Unit)
    }
}