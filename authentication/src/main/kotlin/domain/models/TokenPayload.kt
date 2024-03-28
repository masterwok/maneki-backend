package domain.models

data class TokenPayload(
    val userId: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
)
