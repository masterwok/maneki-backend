package dev.maneki.features.authentication.dtos

import features.authentication.models.Token
import kotlinx.serialization.Serializable

@Serializable
data class TokenDto(
    val token: String,
    val refreshToken: RefreshTokenDto,
) {
    companion object
}

fun TokenDto.Companion.from(source: Token): TokenDto = TokenDto(
    source.token,
    RefreshTokenDto.from(source.refreshToken)
)
