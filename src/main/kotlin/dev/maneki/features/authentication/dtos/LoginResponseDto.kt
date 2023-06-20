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


abstract class Common<T> {
    abstract val data: T?
    abstract val error: String?
}

data class FooData(
    val name: String,
)

class Foo(
    override val data: FooData?,
    override val error: String? = null
) : Common<FooData>()