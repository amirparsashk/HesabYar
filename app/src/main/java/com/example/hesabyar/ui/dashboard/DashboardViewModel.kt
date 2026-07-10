package com.example.hesabyar.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hesabyar.data.repository.FinanceRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class DashboardViewModel(private val financeRepository: FinanceRepository) : ViewModel() {

    private val _state = MutableStateFlow(DashboardContract.State())
    val state: StateFlow<DashboardContract.State> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<DashboardContract.Effect>()
    val effect: SharedFlow<DashboardContract.Effect> = _effect.asSharedFlow()

    init {
        handleIntent(DashboardContract.Intent.LoadDashboardData)
    }

    fun handleIntent(intent: DashboardContract.Intent) {
        when (intent) {
            is DashboardContract.Intent.LoadDashboardData -> loadData()
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            
            combine(
                financeRepository.getOverallTotalIncome(),
                financeRepository.getOverallTotalExpense(),
                financeRepository.getAllTransactions()
            ) { income, expense, transactions ->
                val totalIncome = income ?: 0.0
                val totalExpense = expense ?: 0.0
                DashboardContract.State(
                    balance = totalIncome - totalExpense,
                    totalIncome = totalIncome,
                    totalExpense = totalExpense,
                    recentTransactions = transactions.take(5),
                    isLoading = false
                )
            }.collect { newState ->
                _state.value = newState
            }
        }
    }
}
