package domain.models

import kotlinx.datetime.Instant

data class RefreshToken(
    val userId: Int,
    val token: String,
    val expiresOn: Instant,
) {
    companion object
}


fun RefreshToken.Companion.from(source: dev.maneki.data.Refresh_token): RefreshToken = RefreshToken(
    source.user_id,
    source.token,
    source.expires_on,
)