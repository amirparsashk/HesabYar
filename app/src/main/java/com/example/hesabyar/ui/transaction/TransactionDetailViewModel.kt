package com.example.hesabyar.ui.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hesabyar.data.repository.FinanceRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TransactionDetailViewModel(private val financeRepository: FinanceRepository) : ViewModel() {

    private val _state = MutableStateFlow(TransactionDetailContract.State())
    val state: StateFlow<TransactionDetailContract.State> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<TransactionDetailContract.Effect>()
    val effect: SharedFlow<TransactionDetailContract.Effect> = _effect.asSharedFlow()

    fun handleIntent(intent: TransactionDetailContract.Intent) {
        when (intent) {
            is TransactionDetailContract.Intent.LoadTransaction -> loadTransaction(intent.id)
            is TransactionDetailContract.Intent.DeleteTransaction -> deleteTransaction()
        }
    }

    private fun loadTransaction(id: Long) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val transaction = financeRepository.getTransactionById(id)
            if (transaction != null) {
                val category = financeRepository.getCategoryById(transaction.categoryId)
                _state.value = _state.value.copy(
                    transaction = transaction,
                    category = category,
                    isLoading = false
                )
            } else {
                _state.value = _state.value.copy(isLoading = false)
                _effect.emit(TransactionDetailContract.Effect.ShowError("Transaction not found"))
            }
        }
    }

    private fun deleteTransaction() {
        val transaction = _state.value.transaction ?: return
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            financeRepository.deleteTransaction(transaction)
            _state.value = _state.value.copy(isLoading = false, isDeleted = true)
            _effect.emit(TransactionDetailContract.Effect.NavigateBack)
        }
    }
}
