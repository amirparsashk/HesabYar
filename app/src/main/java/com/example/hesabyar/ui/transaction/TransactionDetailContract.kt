package com.example.hesabyar.ui.transaction

import com.example.hesabyar.data.local.entity.Category
import com.example.hesabyar.data.local.entity.Transaction

class TransactionDetailContract {
    sealed class Intent {
        data class LoadTransaction(val id: Long) : Intent()
        object DeleteTransaction : Intent()
    }

    data class State(
        val transaction: Transaction? = null,
        val category: Category? = null,
        val isLoading: Boolean = false,
        val isDeleted: Boolean = false
    )

    sealed class Effect {
        object NavigateBack : Effect()
        data class ShowError(val message: String) : Effect()
    }
}
