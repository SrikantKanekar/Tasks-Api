package com.example.features.auth.routes

import com.example.config.AppConfig
import com.example.features.auth.data.AuthRepository
import com.example.util.constants.Auth
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.registerAuthRoutes() {

    val authRepository by inject<AuthRepository>()
    val appConfig by inject<AppConfig>()

    routing {
        route("/auth") {
            loginRoute(authRepository, appConfig.jwtConfig)
            registerRoute(authRepository, appConfig.jwtConfig)

            authenticate(Auth.USER_AUTH) {
                tokenRoute(authRepository)
            }
        }
    }
}