package com.example.model

import kotlinx.serialization.Serializable

@Serializable
data class Task(
    val id: String,
    val name: String,
    val description: String,
    var dateTime: String?,
    var completed: Boolean
)
