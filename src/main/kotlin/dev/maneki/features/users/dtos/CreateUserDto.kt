package dev.maneki.features.users.dtos

import features.users.models.CreateUserModel
import kotlinx.serialization.Serializable

@Serializable
data class CreateUserDto(
    val email: String,
    val firstName: String,
    val lastName: String,
    val password: String,
) {
    companion object
}

fun CreateUserDto.toCreateUserModel(): CreateUserModel {
    return CreateUserModel(
        email,
        password,
        firstName,
        lastName,
    )
}
