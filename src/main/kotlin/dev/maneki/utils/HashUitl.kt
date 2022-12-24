package dev.maneki.utils

import at.favre.lib.crypto.bcrypt.BCrypt


object HashUtil {
    private val bcrypt by lazy { BCrypt.withDefaults() }
    private val verifier by lazy { BCrypt.verifyer() }

    fun hashPassword(password: String, cost: Int = 12): String = bcrypt.hashToString(cost, password.toCharArray())

    fun verifyPassword(passwordHash: String, expectedHash: String): Boolean = verifier.verify(
        passwordHash.toCharArray(),
        expectedHash
    ).verified
}
