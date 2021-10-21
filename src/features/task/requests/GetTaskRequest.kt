package com.example.features.task.requests

import kotlinx.serialization.Serializable

@Serializable
data class GetTaskRequest(
    val id: String,
    val index: Int
)