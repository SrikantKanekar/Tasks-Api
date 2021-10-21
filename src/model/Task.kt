package com.example.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class Task(
    val id: String,
    val name: String,
    val description: String,
    var dateTime: Instant?,
    var completed: Boolean
)
