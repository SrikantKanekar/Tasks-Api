package com.example.setUp

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.example.config.AppConfig
import com.example.config.JWTConfig
import com.example.features.auth.data.AuthRepository
import com.example.model.User
import com.example.model.UserPrincipal
import com.example.util.constants.Auth.EMAIL_CLAIM
import com.example.util.constants.Auth.USERNAME_CLAIM
import com.example.util.constants.Auth.USER_AUTH
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import org.koin.ktor.ext.inject

fun Application.authSetup() {

    val authRepository by inject<AuthRepository>()
    val appConfig by inject<AppConfig>()
    val jwtConfig = appConfig.jwtConfig

    install(Authentication) {
        jwt(USER_AUTH) {
            realm = jwtConfig.realm
            verifier(getJwtVerifier(jwtConfig))
            validate { credential ->
                validateJwtToken(jwtConfig, credential, authRepository)
            }
        }
    }
}

fun getJwtVerifier(config: JWTConfig): JWTVerifier {
    return JWT
        .require(Algorithm.HMAC256(config.secret))
        .withIssuer(config.issuer)
        .withAudience(config.audience)
        .build()
}

suspend fun validateJwtToken(
    config: JWTConfig,
    credential: JWTCredential,
    authRepository: AuthRepository,
): UserPrincipal? {
    return if (credential.payload.audience.contains(config.audience)) {
        checkUser(credential, authRepository)
    } else {
        null
    }
}

suspend fun checkUser(
    credential: JWTCredential,
    authRepository: AuthRepository
): UserPrincipal? {
    val username = credential.payload.getClaim(USERNAME_CLAIM).asString()
    val email = credential.payload.getClaim(EMAIL_CLAIM).asString()
    val exists = authRepository.doesUserExist(email)
    return if (exists) UserPrincipal(email, username) else null
}

fun generateJwtToken(jwt: JWTConfig, user: User): String {
    return JWT.create()
        .withIssuer(jwt.issuer)
        .withAudience(jwt.audience)
        .withClaim(USERNAME_CLAIM, user.username)
        .withClaim(EMAIL_CLAIM, user.email)
        .sign(Algorithm.HMAC256(jwt.secret))
}