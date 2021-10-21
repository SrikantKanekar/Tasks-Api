package com.example.features.taskList.requests

import com.example.util.enums.Order
import kotlinx.serialization.Serializable

@Serializable
data class OrderChangeRequest(
    val index: Int,
    val order: Order
)