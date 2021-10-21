package com.example.database.user

import com.example.model.User

interface UserDataSource {

    suspend fun insertUser(user: User)

    suspend fun getUserOrNull(email: String): User?

    suspend fun getUser(email: String): User

    suspend fun getAll(): List<User>

    suspend fun updateUser(user: User)
}