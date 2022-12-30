package features.authentication.models

import kotlinx.datetime.Instant

data class RefreshToken(
    val userId: Int,
    val token: String,
    val expiresOn: Instant,
) {
    companion object
}
