package com.example.features.auth.requests

import com.example.util.validateAndThrowOnFailure
import io.konform.validation.Validation
import io.konform.validation.jsonschema.maxLength
import io.konform.validation.jsonschema.minLength
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val username: String,
    val email: String,
    val password1: String,
    val password2: String
) {
    init {
        Validation<RegisterRequest> {
            RegisterRequest::username {
                minLength(5)
            }
            RegisterRequest::email required {}
            RegisterRequest::password1 required {
                minLength(5)
            }
            RegisterRequest::password2 required {
                minLength(5)
            }
        }.validateAndThrowOnFailure(this)
    }
}
