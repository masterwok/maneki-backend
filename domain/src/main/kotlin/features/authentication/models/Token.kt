package features.authentication.models

data class Token(
    val token: String,
    val refreshToken: RefreshToken,
)
