package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val email: String,
    var password: String,
    var username: String,
    val taskLists: ArrayList<TaskList> = ArrayList(),
)