package domain.models

import kotlinx.datetime.Instant

data class SetUserRefreshTokenModel(
    val userId: Int,
    val token: String,
    val expiresOn: Instant,
)
