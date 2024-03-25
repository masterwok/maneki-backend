package domain.aliases

import domain.models.Token
import domain.models.User

fun interface TokenValidator {
    fun validate(token: String): Boolean
}

fun interface TokenFactory {
    fun create(user: User): Token
}
