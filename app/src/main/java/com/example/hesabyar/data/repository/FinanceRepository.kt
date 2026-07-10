package com.example.hesabyar.data.repository

import com.example.hesabyar.data.local.dao.CategoryDao
import com.example.hesabyar.data.local.dao.TransactionDao
import com.example.hesabyar.data.local.entity.Category
import com.example.hesabyar.data.local.entity.Transaction
import kotlinx.coroutines.flow.Flow

class FinanceRepository(
    private val categoryDao: CategoryDao,
    private val transactionDao: TransactionDao
) {
    // Categories
    fun getAllActiveCategories(): Flow<List<Category>> = categoryDao.getAllActiveCategories()
    suspend fun insertCategory(category: Category) = categoryDao.insert(category)
    suspend fun updateCategory(category: Category) = categoryDao.update(category)
    suspend fun softDeleteCategory(id: Long) = categoryDao.softDelete(id)
    suspend fun getCategoryById(id: Long) = categoryDao.getCategoryById(id)

    // Transactions
    fun getAllTransactions(): Flow<List<Transaction>> = transactionDao.getAllTransactions()
    fun searchTransactions(query: String): Flow<List<Transaction>> = transactionDao.searchTransactions(query)
    fun getTransactionsByCategory(categoryId: Long): Flow<List<Transaction>> = transactionDao.getTransactionsByCategory(categoryId)
    suspend fun insertTransaction(transaction: Transaction) = transactionDao.insert(transaction)
    suspend fun updateTransaction(transaction: Transaction) = transactionDao.update(transaction)
    suspend fun deleteTransaction(transaction: Transaction) = transactionDao.delete(transaction)
    suspend fun getTransactionById(id: Long) = transactionDao.getTransactionById(id)

    fun getTotalIncome(startOfMonth: Long): Flow<Double?> = transactionDao.getTotalIncome(startOfMonth)
    fun getTotalExpense(startOfMonth: Long): Flow<Double?> = transactionDao.getTotalExpense(startOfMonth)
    fun getOverallTotalIncome(): Flow<Double?> = transactionDao.getOverallTotalIncome()
    fun getOverallTotalExpense(): Flow<Double?> = transactionDao.getOverallTotalExpense()
}
