package domain.aliases

import domain.models.TokenPayload
import domain.models.Token

fun interface TokenValidator {
    fun validate(token: String): Boolean
}

fun interface TokenFactory {
    fun create(user: TokenPayload): Token
}
