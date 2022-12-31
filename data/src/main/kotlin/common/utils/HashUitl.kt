package common.utils

import at.favre.lib.crypto.bcrypt.BCrypt


object HashUtil {
    private val bcrypt by lazy { BCrypt.withDefaults() }
    private val verifier by lazy { BCrypt.verifyer() }

    fun hash(
        password: String,
        cost: Int = 12
    ): String = bcrypt.hashToString(cost, password.toCharArray())

    fun verifyPassword(password: String, expectedHash: String): Boolean = verifier.verify(
        password.toCharArray(),
        expectedHash
    ).verified
}
