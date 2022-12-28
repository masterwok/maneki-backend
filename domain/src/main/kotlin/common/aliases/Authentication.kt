package common.aliases

/**
 * Validate a JWT token.
 */
typealias VerifyToken = (token: String) -> Boolean

/**
 * Create a JWT token.
 */
typealias CreateToken = () -> String
