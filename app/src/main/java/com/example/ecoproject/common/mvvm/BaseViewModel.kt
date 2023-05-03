package com.example.ecoproject.common.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn

open class BaseViewModel : ViewModel() {
    protected fun <T> Flow<T>.shareWhileSubscribed(expiry: Long = Long.MAX_VALUE, replay: Int = 1) =
        shareIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(replayExpirationMillis = expiry),
            replay
        )
}