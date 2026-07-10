package com.example.hesabyar.ui.profile

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

class ProfileViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _state = MutableStateFlow(ProfileContract.State())
    val state: StateFlow<ProfileContract.State> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<ProfileContract.Effect>()
    val effect: SharedFlow<ProfileContract.Effect> = _effect.asSharedFlow()

    init {
        handleIntent(ProfileContract.Intent.LoadProfile)
    }

    fun handleIntent(intent: ProfileContract.Intent) {
        when (intent) {
            is ProfileContract.Intent.LoadProfile -> loadProfile()
            is ProfileContract.Intent.Logout -> logout()
        }
    }

    private fun loadProfile() {
        // Simplified for now - usually you'd get the current user from a session manager
        _state.value = _state.value.copy(
            userName = "Amir Mohammad",
            userEmail = "amir.mohammad@example.com"
        )
    }

    private fun logout() {
        viewModelScope.launch {
            _effect.emit(ProfileContract.Effect.NavigateToLogin)
        }
    }
}
