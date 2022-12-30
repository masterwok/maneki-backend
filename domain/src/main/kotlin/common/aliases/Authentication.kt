package common.aliases

import features.authentication.models.Token
import features.users.models.User

fun interface TokenValidator {
    fun validate(token: String): Boolean
}

fun interface TokenFactory {
    fun create(user: User): Token
}
