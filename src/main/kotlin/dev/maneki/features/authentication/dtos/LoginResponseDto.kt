package dev.maneki.features.authentication.dtos

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    val token: String
)
