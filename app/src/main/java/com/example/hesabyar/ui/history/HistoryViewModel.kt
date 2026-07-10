package com.example.hesabyar.ui.history

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

class HistoryViewModel(private val financeRepository: FinanceRepository) : ViewModel() {

    private val _state = MutableStateFlow(HistoryContract.State())
    val state: StateFlow<HistoryContract.State> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<HistoryContract.Effect>()
    val effect: SharedFlow<HistoryContract.Effect> = _effect.asSharedFlow()

    init {
        handleIntent(HistoryContract.Intent.LoadHistory)
    }

    fun handleIntent(intent: HistoryContract.Intent) {
        when (intent) {
            is HistoryContract.Intent.LoadHistory -> loadHistory()
            is HistoryContract.Intent.SearchChanged -> {
                _state.value = _state.value.copy(searchQuery = intent.query)
                loadHistory()
            }
            is HistoryContract.Intent.FilterSelected -> {
                _state.value = _state.value.copy(selectedCategoryId = intent.categoryId)
                loadHistory()
            }
        }
    }

    private fun loadHistory() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            
            val transactionsFlow = if (_state.value.searchQuery.isNotBlank()) {
                financeRepository.searchTransactions(_state.value.searchQuery)
            } else if (_state.value.selectedCategoryId != null) {
                financeRepository.getTransactionsByCategory(_state.value.selectedCategoryId!!)
            } else {
                financeRepository.getAllTransactions()
            }

            combine(
                transactionsFlow,
                financeRepository.getAllActiveCategories()
            ) { transactions, categories ->
                _state.value.copy(
                    transactions = transactions,
                    categories = categories,
                    isLoading = false
                )
            }.collect { newState ->
                _state.value = newState
            }
        }
    }
}
