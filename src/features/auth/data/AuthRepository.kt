package com.example.features.auth.data

import com.example.database.user.UserDataSource
import com.example.model.User
import com.example.util.checkPassword

class AuthRepository(
    private val userDataSource: UserDataSource
) {
    suspend fun doesUserExist(email: String): Boolean {
        val user = userDataSource.getUserOrNull(email)
        return user != null
    }

    suspend fun register(user: User) {
        userDataSource.insertUser(user)
    }

    suspend fun login(email: String, passwordToCheck: String): Boolean {
        val user = userDataSource.getUserOrNull(email) ?: return false
        return checkPassword(passwordToCheck, user.password)
    }

    suspend fun getUser(email: String): User {
        return userDataSource.getUser(email)
    }
}