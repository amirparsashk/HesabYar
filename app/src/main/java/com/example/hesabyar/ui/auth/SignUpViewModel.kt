package com.example.hesabyar.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hesabyar.data.local.entity.User
import com.example.hesabyar.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignUpViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _state = MutableStateFlow(SignUpContract.State())
    val state: StateFlow<SignUpContract.State> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<SignUpContract.Effect>()
    val effect: SharedFlow<SignUpContract.Effect> = _effect.asSharedFlow()

    fun handleIntent(intent: SignUpContract.Intent) {
        when (intent) {
            is SignUpContract.Intent.NameChanged -> _state.value = _state.value.copy(name = intent.name)
            is SignUpContract.Intent.EmailChanged -> _state.value = _state.value.copy(email = intent.email)
            is SignUpContract.Intent.PasswordChanged -> _state.value = _state.value.copy(password = intent.password)
            is SignUpContract.Intent.ConfirmPasswordChanged -> _state.value = _state.value.copy(confirmPassword = intent.confirmPassword)
            is SignUpContract.Intent.SignUpClicked -> signUp()
        }
    }

    private fun signUp() {
        val name = _state.value.name
        val email = _state.value.email
        val password = _state.value.password
        val confirmPassword = _state.value.confirmPassword

        if (name.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            viewModelScope.launch { _effect.emit(SignUpContract.Effect.ShowError("Please fill all fields")) }
            return
        }

        if (password != confirmPassword) {
            viewModelScope.launch { _effect.emit(SignUpContract.Effect.ShowError("Passwords do not match")) }
            return
        }

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val existingUser = userRepository.getUserByEmail(email)
            if (existingUser != null) {
                _state.value = _state.value.copy(isLoading = false)
                _effect.emit(SignUpContract.Effect.ShowError("User already exists"))
            } else {
                val newUser = User(name = name, email = email, password = password)
                userRepository.insertUser(newUser)
                _state.value = _state.value.copy(isLoading = false)
                _effect.emit(SignUpContract.Effect.NavigateToDashboard)
            }
        }
    }
}
