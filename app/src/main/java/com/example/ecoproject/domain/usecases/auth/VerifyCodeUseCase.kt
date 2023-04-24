package com.example.ecoproject.domain.usecases.auth

import com.example.ecoproject.domain.repositories.AuthRepo
import com.example.ecoproject.domain.usecases.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VerifyCodeUseCase @Inject constructor(
    private val authRepo: AuthRepo
) : UseCase<VerifyCodeParams, VerifyCodeResult> {
    override fun invoke(input: VerifyCodeParams): Flow<VerifyCodeResult> = flow {
        try {
            authRepo.verifyCode(input.code)
            emit(VerifyCodeResult.Success)
        } catch (e: Exception) {
            emit(VerifyCodeResult.Error(e))
        }
    }

}

data class VerifyCodeParams(
    val code: String
)

sealed class VerifyCodeResult {
    object Success : VerifyCodeResult()
    data class Error(
        val t: Throwable
    ) : VerifyCodeResult()
}