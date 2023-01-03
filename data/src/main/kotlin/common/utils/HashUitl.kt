package common.utils

import at.favre.lib.crypto.bcrypt.BCrypt
import java.security.MessageDigest


object HashUtil {
    private val bcrypt by lazy { BCrypt.withDefaults() }
    private val verifier by lazy { BCrypt.verifyer() }
    private val messageDigestSha256 by lazy { MessageDigest.getInstance("SHA-256") }

    fun hashSha256(value: String): String = messageDigestSha256
        .digest(value.toByteArray())
        .joinToString("") { String.format("%02x", it) }

    fun hashPassword(
        password: String,
        cost: Int = 12
    ): String = bcrypt.hashToString(cost, password.toCharArray())

    fun verifyPassword(password: String, expectedHash: String): Boolean = verifier.verify(
        password.toCharArray(),
        expectedHash
    ).verified
}
