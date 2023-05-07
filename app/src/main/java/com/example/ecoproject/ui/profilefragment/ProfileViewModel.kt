package com.example.ecoproject.ui.profilefragment

import androidx.lifecycle.viewModelScope
import com.example.ecoproject.common.mvvm.BaseViewModel
import com.example.ecoproject.domain.repositories.AuthState
import com.example.ecoproject.domain.usecases.auth.GetAuthStateUseCase
import com.example.ecoproject.domain.usecases.auth.SignOutUseCase
import com.example.ecoproject.ui.utils.UIUtils.collectIn
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase,
    getAuthStateUseCase: GetAuthStateUseCase
) : BaseViewModel() {

    val user = getAuthStateUseCase(Unit)
        .filter { it is AuthState.Authed }
        .map { (it as AuthState.Authed).user }
        .shareWhileSubscribed()

    fun signOut() {
        signOutUseCase(Unit).collectIn(viewModelScope) { }
    }
}