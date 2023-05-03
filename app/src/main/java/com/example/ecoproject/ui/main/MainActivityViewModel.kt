package com.example.ecoproject.ui.main

import com.example.ecoproject.common.mvvm.BaseViewModel
import com.example.ecoproject.domain.usecases.auth.GetAuthStateUseCase
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    getAuthStateUseCase: GetAuthStateUseCase
) : BaseViewModel() {
    val authState = getAuthStateUseCase(Unit).shareWhileSubscribed()
}