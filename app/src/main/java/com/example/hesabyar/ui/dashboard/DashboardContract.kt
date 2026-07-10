package com.example.hesabyar.ui.dashboard

import com.example.hesabyar.data.local.entity.Transaction

class DashboardContract {
    sealed class Intent {
        object LoadDashboardData : Intent()
    }

    data class State(
        val balance: Double = 0.0,
        val totalIncome: Double = 0.0,
        val totalExpense: Double = 0.0,
        val recentTransactions: List<Transaction> = emptyList(),
        val isLoading: Boolean = false
    )

    sealed class Effect {
        data class ShowError(val message: String) : Effect()
    }
}
