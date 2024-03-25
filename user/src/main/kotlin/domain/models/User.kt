package domain.models

import kotlinx.datetime.Instant


data class User(
    val id: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val createdOn: Instant,
    val updatedOn: Instant,
) {
    companion object
}


fun User.Companion.from(source: dev.maneki.data.User): User = User(
    id = source.id,
    email = source.email,
    firstName = source.first_name,
    lastName = source.last_name,
    createdOn = source.created_on,
    updatedOn = source.updated_on
)