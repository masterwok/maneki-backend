package features.authentication.models

data class SetUserRefreshTokenModel(
    val userId: Int,
    val refreshToken: RefreshToken,
)
