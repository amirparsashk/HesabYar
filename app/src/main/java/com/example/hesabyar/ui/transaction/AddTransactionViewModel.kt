package com.example.hesabyar.ui.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hesabyar.data.local.entity.Transaction
import com.example.hesabyar.data.repository.FinanceRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddTransactionViewModel(private val financeRepository: FinanceRepository) : ViewModel() {

    private val _state = MutableStateFlow(AddTransactionContract.State())
    val state: StateFlow<AddTransactionContract.State> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<AddTransactionContract.Effect>()
    val effect: SharedFlow<AddTransactionContract.Effect> = _effect.asSharedFlow()

    init {
        handleIntent(AddTransactionContract.Intent.LoadCategories)
    }

    fun handleIntent(intent: AddTransactionContract.Intent) {
        when (intent) {
            is AddTransactionContract.Intent.LoadCategories -> loadCategories()
            is AddTransactionContract.Intent.TypeChanged -> _state.value = _state.value.copy(type = intent.type)
            is AddTransactionContract.Intent.TitleChanged -> _state.value = _state.value.copy(title = intent.title)
            is AddTransactionContract.Intent.AmountChanged -> _state.value = _state.value.copy(amount = intent.amount)
            is AddTransactionContract.Intent.CategorySelected -> _state.value = _state.value.copy(categoryId = intent.categoryId)
            is AddTransactionContract.Intent.DateChanged -> _state.value = _state.value.copy(date = intent.date)
            is AddTransactionContract.Intent.NoteChanged -> _state.value = _state.value.copy(note = intent.note)
            is AddTransactionContract.Intent.SaveTransaction -> saveTransaction()
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            financeRepository.getAllActiveCategories().collect { categories ->
                _state.value = _state.value.copy(
                    categories = categories,
                    categoryId = categories.firstOrNull()?.id
                )
            }
        }
    }

    private fun saveTransaction() {
        val title = _state.value.title
        val amount = _state.value.amount.toDoubleOrNull()
        val categoryId = _state.value.categoryId

        if (title.isBlank() || amount == null || categoryId == null) {
            viewModelScope.launch { _effect.emit(AddTransactionContract.Effect.ShowError("Invalid input")) }
            return
        }

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val transaction = Transaction(
                title = title,
                amount = amount,
                categoryId = categoryId,
                date = _state.value.date,
                type = _state.value.type,
                note = _state.value.note
            )
            financeRepository.insertTransaction(transaction)
            _state.value = _state.value.copy(isLoading = false, isSaved = true)
            _effect.emit(AddTransactionContract.Effect.NavigateBack)
        }
    }
}
