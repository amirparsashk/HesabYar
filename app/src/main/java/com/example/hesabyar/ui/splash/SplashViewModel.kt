package com.example.hesabyar.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    private val _state = MutableStateFlow(SplashContract.State())
    val state: StateFlow<SplashContract.State> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<SplashContract.Effect>()
    val effect: SharedFlow<SplashContract.Effect> = _effect.asSharedFlow()

    fun handleIntent(intent: SplashContract.Intent) {
        when (intent) {
            is SplashContract.Intent.StartTimer -> startTimer()
        }
    }

    private fun startTimer() {
        viewModelScope.launch {
            // Animate progress bar (simulated)
            for (i in 1..100) {
                delay(30)
                _state.value = _state.value.copy(progress = i / 100f)
            }
            _effect.emit(SplashContract.Effect.NavigateToMain)
        }
    }
}
