package com.example.hesabyar

import android.app.Application
import com.example.hesabyar.data.local.database.AppDatabase
import com.example.hesabyar.data.local.entity.Category
import com.example.hesabyar.data.repository.FinanceRepository
import com.example.hesabyar.data.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class HesabYarApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val userRepository by lazy { UserRepository(database.userDao()) }
    val financeRepository by lazy { FinanceRepository(database.categoryDao(), database.transactionDao()) }

    private val applicationScope = CoroutineScope(SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        seedDatabase()
    }

    private fun seedDatabase() {
        applicationScope.launch {
            val categoryDao = database.categoryDao()
            // Check if categories are already seeded
            // For simplicity, I'll just check if there's any category
            // but in a real app I might use a more robust check or a migration
            // Since it's a Flow, I'll take(1)
            // But for seeding, maybe better to check synchronously if possible or use a flag
            // I'll just use a simple insert if empty approach
        }
    }
}
