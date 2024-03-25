package domain.models

data class Token(
    val token: String,
    val refreshToken: RefreshToken,
)
