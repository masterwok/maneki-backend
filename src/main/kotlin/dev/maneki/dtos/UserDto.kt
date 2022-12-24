package dev.maneki.dtos

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val email: String,
    val firstName: String,
    val lastName: String,
    val createdOn: Instant,
    val updatedOn: Instant,
)
