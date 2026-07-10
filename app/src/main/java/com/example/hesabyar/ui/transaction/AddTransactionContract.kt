package com.example.hesabyar.ui.transaction

import com.example.hesabyar.data.local.entity.Category

class AddTransactionContract {
    sealed class Intent {
        object LoadCategories : Intent()
        data class TypeChanged(val type: String) : Intent()
        data class TitleChanged(val title: String) : Intent()
        data class AmountChanged(val amount: String) : Intent()
        data class CategorySelected(val categoryId: Long) : Intent()
        data class DateChanged(val date: Long) : Intent()
        data class NoteChanged(val note: String) : Intent()
        object SaveTransaction : Intent()
    }

    data class State(
        val type: String = "Expense",
        val title: String = "",
        val amount: String = "",
        val categoryId: Long? = null,
        val date: Long = System.currentTimeMillis(),
        val note: String = "",
        val categories: List<Category> = emptyList(),
        val isLoading: Boolean = false,
        val isSaved: Boolean = false
    )

    sealed class Effect {
        object NavigateBack : Effect()
        data class ShowError(val message: String) : Effect()
    }
}
