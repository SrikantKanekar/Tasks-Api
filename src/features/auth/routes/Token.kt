package com.example.features.auth.routes

import com.example.features.auth.data.AuthRepository
import com.example.model.UserPrincipal
import com.example.util.constants.Auth.EMAIL_PASSWORD_INCORRECT
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.tokenRoute(authRepository: AuthRepository) {
    put("/token") {
        val token = call.receive<String>()
        val principal = call.principal<UserPrincipal>()!!
        authRepository.updateToken(principal.email, token)
        call.respond(HttpStatusCode.BadRequest, EMAIL_PASSWORD_INCORRECT)
    }
}