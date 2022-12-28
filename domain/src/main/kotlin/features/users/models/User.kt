package features.users.models

import kotlinx.datetime.Instant


data class User(
    val id: Int?,
    val email: String,
    val firstName: String,
    val lastName: String,
    val createdOn: Instant,
    val updatedOn: Instant,
) {
    companion object
}
