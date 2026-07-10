package com.example.hesabyar.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val amount: Double,
    val title: String,
    val categoryId: Long,
    val date: Long,
    val type: String, // "Income" or "Expense"
    val note: String? = null
)
