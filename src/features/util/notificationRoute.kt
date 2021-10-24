package com.example.features.util

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.notificationRoute() {

    routing {
        route("notification/test") {
            post {
                val registrationToken = "cMmc9VneQ0mzbI-RxQ6spS:APA91bHHlD_EZHCUcgw2TQT0EXIDzflT_E7dXwnJACHsK97TQ6FfI1FZEJBn63VttR5pbOyQPzg4LpfQOr-tnEORNCTNUBfvaT22c3Pso3uiORiGCEEbH1TzSjuH2CZFkjUPt-HpgA8i"
                val notification = Notification.builder()
                    .setTitle("title from server")
                    .setBody("body")
                    .build()

                val message: Message = Message.builder()
                    .setNotification(notification)
                    .setToken(registrationToken)
                    .build()

                val response = FirebaseMessaging.getInstance().send(message)
                println("Successfully sent message: $response")

                call.respond(HttpStatusCode.OK)
            }
        }
    }
}