package dev.maneki.features.authentication.dtos

import features.authentication.models.Token
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    val token: String,
    val refreshToken: String,
) {
    companion object
}

fun LoginResponseDto.Companion.from(source: Token): LoginResponseDto = LoginResponseDto(
    source.token,
    source.refreshToken.token,
)
