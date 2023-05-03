package com.example.ecoproject.ui.profilefragment

import com.example.ecoproject.common.mvvm.BaseViewModel
import com.example.ecoproject.domain.usecases.auth.SignOutUseCase
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase
) : BaseViewModel() {

}