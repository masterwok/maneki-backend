package dev.maneki.features.authentication.dtos

import features.authentication.usecases.LoginParam
import kotlinx.serialization.Serializable


@Serializable
data class LoginRequestDto(
    val email: String,
    val password: String
) {
    companion object
}

fun LoginRequestDto.toLoginModel(): LoginParam = LoginParam(email, password)
