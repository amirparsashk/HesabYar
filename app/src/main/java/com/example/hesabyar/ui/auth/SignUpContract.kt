package com.example.hesabyar.ui.auth

class SignUpContract {
    sealed class Intent {
        data class NameChanged(val name: String) : Intent()
        data class EmailChanged(val email: String) : Intent()
        data class PasswordChanged(val password: String) : Intent()
        data class ConfirmPasswordChanged(val confirmPassword: String) : Intent()
        object SignUpClicked : Intent()
    }

    data class State(
        val name: String = "",
        val email: String = "",
        val password: String = "",
        val confirmPassword: String = "",
        val isLoading: Boolean = false,
        val error: String? = null
    )

    sealed class Effect {
        object NavigateToDashboard : Effect()
        data class ShowError(val message: String) : Effect()
    }
}
