package com.example.ecoproject.ui.auth.signupfragment

import androidx.lifecycle.viewModelScope
import com.example.ecoproject.common.mvvm.BaseViewModel
import com.example.ecoproject.domain.usecases.auth.SendSMSParams
import com.example.ecoproject.domain.usecases.auth.SendSMSUseCase
import com.example.ecoproject.domain.usecases.auth.VerificationStateUseCase
import com.example.ecoproject.ui.utils.UIUtils.collectIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    private val sendSMSUseCase: SendSMSUseCase,
    private val getVerificationStateUseCase: VerificationStateUseCase
) : BaseViewModel() {
    private val phoneRegex = """^\+7 \(\d{3}\) \d{3}-\d{2}-\d{2}$""".toRegex()

    val phoneNumber = MutableStateFlow("")

    val verificationState = getVerificationStateUseCase(Unit).shareWhileSubscribed()

    val isPhoneCorrect = phoneNumber.map { phoneRegex.matches(it) }.shareWhileSubscribed()

    fun sendSMS() {
        phoneNumber.take(1).flatMapConcat {
            sendSMSUseCase(SendSMSParams(it))
        }.take(1).collectIn(viewModelScope) {}
    }
}