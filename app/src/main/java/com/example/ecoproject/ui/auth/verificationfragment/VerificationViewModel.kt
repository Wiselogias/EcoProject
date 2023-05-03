package com.example.ecoproject.ui.auth.verificationfragment

import com.example.ecoproject.common.mvvm.BaseViewModel
import com.example.ecoproject.domain.usecases.auth.VerifyCodeParams
import com.example.ecoproject.domain.usecases.auth.VerifyCodeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import javax.inject.Inject

class VerificationViewModel @Inject constructor(
    private val verifyCodeUseCase: VerifyCodeUseCase
) : BaseViewModel() {
    val code = MutableStateFlow("")
    val isCode = code.map { it.length > 4 }.shareWhileSubscribed()


    fun validateCode() = code.take(1).flatMapConcat { verifyCodeUseCase(VerifyCodeParams(it)) }
}