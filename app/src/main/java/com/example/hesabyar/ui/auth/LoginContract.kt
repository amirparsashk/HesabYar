package com.example.hesabyar.ui.auth

class LoginContract {
    sealed class Intent {
        data class EmailChanged(val email: String) : Intent()
        data class PasswordChanged(val password: String) : Intent()
        object TogglePasswordVisibility : Intent()
        object LoginClicked : Intent()
    }

    data class State(
        val email: String = "",
        val password: String = "",
        val isPasswordVisible: Boolean = false,
        val isLoading: Boolean = false,
        val error: String? = null
    )

    sealed class Effect {
        object NavigateToDashboard : Effect()
        data class ShowError(val message: String) : Effect()
    }
}
