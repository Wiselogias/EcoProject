package com.example.ecoproject.ui.auth.signupfragment

import android.app.Activity
import com.example.ecoproject.common.mvvm.BaseViewModel
import com.example.ecoproject.domain.repositories.AuthState
import com.example.ecoproject.domain.usecases.auth.SendSMSParams
import com.example.ecoproject.domain.usecases.auth.SendSMSUseCase
import com.example.ecoproject.domain.usecases.auth.VerificationStateUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import javax.inject.Inject

@OptIn(FlowPreview::class)
class SignUpViewModel @Inject constructor(
    private val sendSMSUseCase: SendSMSUseCase,
    getVerificationStateUseCase: VerificationStateUseCase
) : BaseViewModel() {
//    private val phoneRegex = """^\+7 \(\d{3}\) \d{3}-\d{2}-\d{2}$""".toRegex()

    val phoneNumber = MutableStateFlow("")

    val verificationState = getVerificationStateUseCase(Unit).shareWhileSubscribed()

    val isPhoneCorrect = phoneNumber.map { it.startsWith("+") }.shareWhileSubscribed()

    fun sendSMS(activity: Activity) = phoneNumber.take(1).flatMapConcat {
        sendSMSUseCase(SendSMSParams(it, activity))
    }.filter { it is AuthState.Error }.take(1)
}