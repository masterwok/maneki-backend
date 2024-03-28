package utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import domain.aliases.TokenFactory
import domain.aliases.TokenValidator
import domain.models.RefreshToken
import domain.models.Token
import extensions.randomAlphaNumeric
import kotlinx.datetime.Clock
import java.util.*
import kotlin.time.Duration.Companion.days

object JwtUtil {
    const val CLAIM_KEY_EMAIL = "email"
    const val CLAIM_KEY_FIRST_NAME = "lastName"
    const val CLAIM_KEY_LAST_NAME = "firstName"

    private val refreshTokenExpirationDuration = 60.days

    private const val oneHourMs = 60_000 * 60
    private const val REFRESH_TOKEN_LENGTH = 50

    private val verifier = JWT
        .require(Algorithm.HMAC256(secret))
        .build()

    val secret: String
        get() = System.getenv("JWT_SECRET").apply {
            if (isNullOrEmpty()) throw IllegalArgumentException("Failed to find JWT_SECRET environment variable required to sign tokens.")
        }

    private fun createRefreshToken(userId: Int): RefreshToken {
        return RefreshToken(
            userId,
            String.randomAlphaNumeric(REFRESH_TOKEN_LENGTH),
            Clock.System.now().plus(refreshTokenExpirationDuration)
        )
    }

    val createToken = TokenFactory { payload ->
        Token(
            token = JWT
                .create()
                .withExpiresAt(Date(System.currentTimeMillis() + oneHourMs))
                .withClaim(CLAIM_KEY_EMAIL, payload.email)
                .withClaim(CLAIM_KEY_FIRST_NAME, payload.firstName)
                .withClaim(CLAIM_KEY_LAST_NAME, payload.lastName)
                .sign(Algorithm.HMAC256(secret)),
            refreshToken = createRefreshToken(payload.userId)
        )
    }

    val validateToken = TokenValidator { token ->
        try {
            verifier.verify(token)
            true
        } catch (ex: Exception) {
            false
        }
    }
}

