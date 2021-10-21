package com.example.features.auth.routes

import com.example.config.JWTConfig
import com.example.features.auth.data.AuthRepository
import com.example.features.auth.requests.LoginRequest
import com.example.setUp.generateJwtToken
import com.example.util.constants.Auth.EMAIL_PASSWORD_INCORRECT
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.loginRoute(authRepository: AuthRepository, jwt: JWTConfig) {
    post("/login") {
        val (email, password) = call.receive<LoginRequest>()

        val isPasswordCorrect = authRepository.login(email, password)
        if (isPasswordCorrect) {
            val user = authRepository.getUser(email)
            val token = generateJwtToken(jwt, user)
            call.respond(token)
        } else {
            call.respond(HttpStatusCode.BadRequest, EMAIL_PASSWORD_INCORRECT)
        }
    }
}