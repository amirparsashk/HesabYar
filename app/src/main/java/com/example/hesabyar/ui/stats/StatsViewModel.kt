package com.example.hesabyar.ui.stats

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

class StatsViewModel(private val financeRepository: FinanceRepository) : ViewModel() {

    private val _state = MutableStateFlow(StatsContract.State())
    val state: StateFlow<StatsContract.State> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<StatsContract.Effect>()
    val effect: SharedFlow<StatsContract.Effect> = _effect.asSharedFlow()

    init {
        handleIntent(StatsContract.Intent.LoadStats)
    }

    fun handleIntent(intent: StatsContract.Intent) {
        when (intent) {
            is StatsContract.Intent.LoadStats -> loadStats()
        }
    }

    private fun loadStats() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            
            combine(
                financeRepository.getOverallTotalIncome(),
                financeRepository.getOverallTotalExpense(),
                financeRepository.getAllTransactions(),
                financeRepository.getAllActiveCategories()
            ) { income, expense, transactions, categories ->
                val totalIncome = income ?: 0.0
                val totalExpense = expense ?: 0.0
                
                val breakdown = categories.map { category ->
                    val categoryAmount = transactions
                        .filter { it.categoryId == category.id && it.type == "Expense" }
                        .sumOf { it.amount }
                    
                    StatsContract.CategoryStat(
                        categoryName = category.name,
                        amount = categoryAmount,
                        percentage = if (totalExpense > 0) (categoryAmount / totalExpense).toFloat() else 0f
                    )
                }.filter { it.amount > 0 }

                StatsContract.State(
                    totalIncome = totalIncome,
                    totalExpense = totalExpense,
                    categoryBreakdown = breakdown,
                    isLoading = false
                )
            }.collect { newState ->
                _state.value = newState
            }
        }
    }
}
