package dev.maneki.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

object JwtUtil {
    private const val oneHourMs = 60_000 * 60

    private val verifier = JWT
        .require(Algorithm.HMAC256(secret))
        .build()

    val secret: String
        get() = System.getenv("JWT_SECRET").apply {
            if (isNullOrEmpty()) throw IllegalArgumentException("Failed to initialize database driver: JDBC_URL environment variable required (SQLite or MySQL).")
        }

    fun createToken(): String = JWT
        .create()
        .withExpiresAt(Date(System.currentTimeMillis() + oneHourMs))
        .sign(Algorithm.HMAC256(secret))

    fun verifyToken(token: String): Boolean = try {
        verifier.verify(token)
        true
    } catch (ex: Exception) {
        false
    }
}

