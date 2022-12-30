package features.users.extensions

import features.authentication.models.RefreshToken
import features.users.models.User

fun User.Companion.from(source: dev.maneki.data.User): User {
    return User(
        id = source.id,
        email = source.email,
        firstName = source.first_name,
        lastName = source.last_name,
        createdOn = source.created_on,
        updatedOn = source.updated_on
    )
}

fun RefreshToken.Companion.from(source: dev.maneki.data.Refresh_token): RefreshToken {
    return RefreshToken(
        source.user_id,
        source.token,
        source.expires_on,
    )
}
