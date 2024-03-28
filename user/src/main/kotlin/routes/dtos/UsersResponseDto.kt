package routes.dtos

import kotlinx.serialization.Serializable

@Serializable
data class UsersResponseDto(
    val users: List<UserDto>,
)
