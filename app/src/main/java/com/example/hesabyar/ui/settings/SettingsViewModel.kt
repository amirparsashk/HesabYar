package com.example.hesabyar.ui.settings

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

class SettingsViewModel(private val userRepository: UserRepository) : ViewModel() {

    private val _state = MutableStateFlow(SettingsContract.State())
    val state: StateFlow<SettingsContract.State> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<SettingsContract.Effect>()
    val effect: SharedFlow<SettingsContract.Effect> = _effect.asSharedFlow()

    init {
        handleIntent(SettingsContract.Intent.LoadSettings)
    }

    fun handleIntent(intent: SettingsContract.Intent) {
        when (intent) {
            is SettingsContract.Intent.LoadSettings -> loadSettings()
            is SettingsContract.Intent.DarkModeChanged -> _state.value = _state.value.copy(isDarkMode = intent.enabled)
            is SettingsContract.Intent.NotificationsChanged -> _state.value = _state.value.copy(isNotificationsEnabled = intent.enabled)
            is SettingsContract.Intent.Logout -> logout()
        }
    }

    private fun loadSettings() {
        _state.value = _state.value.copy(
            userName = "Rachel Wong",
            userEmail = "rachelwong@gmail.com"
        )
    }

    private fun logout() {
        viewModelScope.launch {
            _effect.emit(SettingsContract.Effect.NavigateToLogin)
        }
    }
}
