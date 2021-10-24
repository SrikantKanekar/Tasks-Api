package com.example.setUp

import com.example.features.taskList.data.TaskListRepository
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import io.ktor.application.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.ktor.ext.inject
import kotlin.time.ExperimentalTime

@ExperimentalTime
fun Application.firebaseSetup() {
    FirebaseApp.initializeApp()

    val taskListRepository by inject<TaskListRepository>()

    launch {
        while (true) {
            val notifications = taskListRepository.getNotifications()
            notifications.forEach {
                sendNotification(it.title, it.token)
            }
            println("notifications sent ${notifications.size}")
            delay(60000)
        }
    }
}

fun sendNotification(title: String, token: String) {
    val notification = Notification.builder()
        .setTitle(title)
        .build()

    val message: Message = Message.builder()
        .setNotification(notification)
        .setToken(token)
        .build()

    val response = FirebaseMessaging.getInstance().send(message)
    println("Successfully sent message: $response")
}