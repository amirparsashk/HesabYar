package com.example.hesabyar.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.hesabyar.data.local.dao.CategoryDao
import com.example.hesabyar.data.local.dao.TransactionDao
import com.example.hesabyar.data.local.dao.UserDao
import com.example.hesabyar.data.local.entity.Category
import com.example.hesabyar.data.local.entity.Transaction
import com.example.hesabyar.data.local.entity.User

@Database(entities = [User::class, Category::class, Transaction::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun categoryDao(): CategoryDao
    abstract fun transactionDao(): TransactionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "hesabyar_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
