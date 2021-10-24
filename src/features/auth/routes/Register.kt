package com.example.features.auth.routes

import com.example.config.JWTConfig
import com.example.features.auth.data.AuthRepository
import com.example.features.auth.requests.RegisterRequest
import com.example.model.TaskList
import com.example.model.User
import com.example.setUp.generateJwtToken
import com.example.util.constants.Auth.EMAIL_ALREADY_TAKEN
import com.example.util.constants.Auth.PASSWORDS_DO_NOT_MATCH
import com.example.util.enums.Order
import com.example.util.generateHash
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlin.random.Random

fun Route.registerRoute(authRepository: AuthRepository, jwt: JWTConfig) {
    post("/register") {
        val (username, email, password1, password2) = call.receive<RegisterRequest>()

        if (password1 == password2) {
            val userExists = authRepository.doesUserExist(email)
            if (!userExists) {
                val list = ArrayList<TaskList>()
                val taskList = TaskList(
                    id = Random.nextInt(0, 10000),
                    name = "My Tasks",
                    tasks = ArrayList(),
                    order = Order.Normal
                )
                list.add(taskList)
                val newUser = User(email, generateHash(password1), username, taskLists = list)
                authRepository.register(newUser)
                val token = generateJwtToken(jwt, newUser)
                call.respond(token)
            } else {
                call.respond(HttpStatusCode.BadRequest, EMAIL_ALREADY_TAKEN)
            }
        } else {
            call.respond(HttpStatusCode.BadRequest, PASSWORDS_DO_NOT_MATCH)
        }
    }
}