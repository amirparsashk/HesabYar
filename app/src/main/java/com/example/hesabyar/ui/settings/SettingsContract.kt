package com.example.hesabyar.ui.settings

class SettingsContract {
    sealed class Intent {
        object LoadSettings : Intent()
        data class DarkModeChanged(val enabled: Boolean) : Intent()
        data class NotificationsChanged(val enabled: Boolean) : Intent()
        object Logout : Intent()
    }

    data class State(
        val userName: String = "",
        val userEmail: String = "",
        val isDarkMode: Boolean = false,
        val isNotificationsEnabled: Boolean = true,
        val isLoading: Boolean = false
    )

    sealed class Effect {
        object NavigateToLogin : Effect()
    }
}
