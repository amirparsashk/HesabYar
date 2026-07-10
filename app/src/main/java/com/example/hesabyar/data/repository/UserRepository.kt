package com.example.hesabyar.data.repository

import com.example.hesabyar.data.local.dao.UserDao
import com.example.hesabyar.data.local.entity.User

class UserRepository(private val userDao: UserDao) {
    
    suspend fun insertUser(user: User) {
        userDao.insert(user)
    }

    suspend fun login(email: String, password: String): User? {
        return userDao.login(email, password)
    }

    suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }
}
