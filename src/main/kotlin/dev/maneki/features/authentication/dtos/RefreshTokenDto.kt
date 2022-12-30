package dev.maneki.features.authentication.dtos

import features.authentication.models.RefreshToken
import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenDto(
    val token: String,
) {
    companion object
}

fun RefreshTokenDto.Companion.from(source: RefreshToken): RefreshTokenDto = RefreshTokenDto(
    source.token,
)
