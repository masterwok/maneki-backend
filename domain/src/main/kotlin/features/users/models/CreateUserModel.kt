package features.users.models

data class CreateUserModel(
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String,
)
