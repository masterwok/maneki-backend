package dev.maneki.features.authentication.dtos

import domain.usecases.LoginParam
import kotlinx.serialization.Serializable


@Serializable
data class LoginRequestDto(
    val username: String,
    val password: String
) {
    companion object
}

fun LoginRequestDto.toLoginModel(): LoginParam = LoginParam(username, password)
