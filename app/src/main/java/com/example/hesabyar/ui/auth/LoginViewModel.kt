package com.example.hesabyar.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hesabyar.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _state = MutableStateFlow(LoginContract.State())
    val state: StateFlow<LoginContract.State> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<LoginContract.Effect>()
    val effect: SharedFlow<LoginContract.Effect> = _effect.asSharedFlow()

    fun handleIntent(intent: LoginContract.Intent) {
        when (intent) {
            is LoginContract.Intent.EmailChanged -> {
                _state.value = _state.value.copy(email = intent.email, error = null)
            }
            is LoginContract.Intent.PasswordChanged -> {
                _state.value = _state.value.copy(password = intent.password, error = null)
            }
            is LoginContract.Intent.TogglePasswordVisibility -> {
                _state.value = _state.value.copy(isPasswordVisible = !_state.value.isPasswordVisible)
            }
            is LoginContract.Intent.LoginClicked -> login()
        }
    }

    private fun login() {
        val email = _state.value.email
        val password = _state.value.password

        if (email.isBlank() || password.isBlank()) {
            viewModelScope.launch {
                _effect.emit(LoginContract.Effect.ShowError("Please fill in all fields"))
            }
            return
        }

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val user = userRepository.login(email, password)
            _state.value = _state.value.copy(isLoading = false)

            if (user != null) {
                _effect.emit(LoginContract.Effect.NavigateToDashboard)
            } else {
                _effect.emit(LoginContract.Effect.ShowError("Invalid email or password"))
            }
        }
    }
}
