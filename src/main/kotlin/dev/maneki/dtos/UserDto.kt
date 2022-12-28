package dev.maneki.dtos

import features.users.models.User
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val email: String,
    val firstName: String,
    val lastName: String,
    val createdOn: Instant,
    val updatedOn: Instant,
) {
    companion object
}

fun UserDto.Companion.from(user: User): UserDto = UserDto(
    user.email,
    user.firstName,
    user.lastName,
    user.createdOn,
    user.updatedOn,
)
