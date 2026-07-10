package com.example.hesabyar.ui.history

import com.example.hesabyar.data.local.entity.Category
import com.example.hesabyar.data.local.entity.Transaction

class HistoryContract {
    sealed class Intent {
        object LoadHistory : Intent()
        data class SearchChanged(val query: String) : Intent()
        data class FilterSelected(val categoryId: Long?) : Intent()
    }

    data class State(
        val transactions: List<Transaction> = emptyList(),
        val categories: List<Category> = emptyList(),
        val searchQuery: String = "",
        val selectedCategoryId: Long? = null,
        val isLoading: Boolean = false
    )

    sealed class Effect {
        data class ShowError(val message: String) : Effect()
    }
}
