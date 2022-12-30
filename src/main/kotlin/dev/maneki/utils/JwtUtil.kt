package dev.maneki.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import common.aliases.TokenFactory
import common.aliases.TokenValidator
import features.authentication.models.RefreshToken
import features.authentication.models.Token
import features.users.models.User
import kotlinx.datetime.Clock
import java.security.SecureRandom
import java.util.*
import kotlin.time.Duration.Companion.days

object JwtUtil {
    private val refreshTokenExpirationDuration = 60.days

    private const val oneHourMs = 60_000 * 60
    private const val CLAIM_KEY_EMAIL = "email"
    private const val CLAIM_KEY_FIRST_NAME = "lastName"
    private const val CLAIM_KEY_LAST_NAME = "firstName"

    private val verifier = JWT
        .require(Algorithm.HMAC256(secret))
        .build()

    private val secureRandom = SecureRandom()
    private val base64Encoder = Base64.getEncoder()

    val secret: String
        get() = System.getenv("JWT_SECRET").apply {
            if (isNullOrEmpty()) throw IllegalArgumentException("Failed to initialize database driver: JDBC_URL environment variable required (SQLite or MySQL).")
        }

    private fun createRefreshToken(user: User) = with(ByteArray(128)) {
        secureRandom.nextBytes(this)

        RefreshToken(
            user.id!!,
            base64Encoder.encodeToString(this),
            Clock.System.now().plus(refreshTokenExpirationDuration)
        )
    }

    val createToken = TokenFactory { user ->
        Token(
            token = JWT
                .create()
                .withExpiresAt(Date(System.currentTimeMillis() + oneHourMs))
                .withClaim(CLAIM_KEY_EMAIL, user.email)
                .withClaim(CLAIM_KEY_FIRST_NAME, user.firstName)
                .withClaim(CLAIM_KEY_LAST_NAME, user.lastName)
                .sign(Algorithm.HMAC256(secret)),
            refreshToken = createRefreshToken(user)
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

