package com.example.hesabyar.ui.stats

class StatsContract {
    sealed class Intent {
        object LoadStats : Intent()
    }

    data class State(
        val totalIncome: Double = 0.0,
        val totalExpense: Double = 0.0,
        val categoryBreakdown: List<CategoryStat> = emptyList(),
        val isLoading: Boolean = false
    )

    data class CategoryStat(
        val categoryName: String,
        val amount: Double,
        val percentage: Float,
        val limit: Double = 1000.0 // Hardcoded for now
    )

    sealed class Effect {
        data class ShowError(val message: String) : Effect()
    }
}
