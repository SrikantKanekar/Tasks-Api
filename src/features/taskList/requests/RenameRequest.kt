package com.example.features.taskList.requests

import kotlinx.serialization.Serializable

@Serializable
data class RenameRequest(
    val index: Int,
    val name: String
)

