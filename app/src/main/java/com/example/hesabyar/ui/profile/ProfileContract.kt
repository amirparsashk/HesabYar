package com.example.hesabyar.ui.profile

class ProfileContract {
    sealed class Intent {
        object LoadProfile : Intent()
        object Logout : Intent()
    }

    data class State(
        val userName: String = "",
        val userEmail: String = "",
        val accountTier: String = "Premium",
        val memberSince: String = "Jan 2024",
        val isLoading: Boolean = false
    )

    sealed class Effect {
        object NavigateToLogin : Effect()
    }
}
