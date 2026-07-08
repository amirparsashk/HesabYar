package com.example.hesabyar.ui.splash

class SplashContract {
    sealed class Intent {
        object StartTimer : Intent()
    }

    data class State(
        val isLoading: Boolean = true,
        val progress: Float = 0f
    )

    sealed class Effect {
        object NavigateToMain : Effect()
    }
}
