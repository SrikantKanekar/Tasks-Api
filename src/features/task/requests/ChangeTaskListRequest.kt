package com.example.features.task.requests

import com.example.model.Task
import kotlinx.serialization.Serializable

@Serializable
data class ChangeTaskListRequest(
    val task: Task,
    val name: String,
    val index: Int
)
