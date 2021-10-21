package com.example.features.task.requests

import com.example.model.Task
import kotlinx.serialization.Serializable

@Serializable
data class TaskRequest(
    val task: Task,
    val index: Int
)

