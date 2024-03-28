package extensions

import utils.JwtUtil
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.util.pipeline.*

/**
 * Get the email of the currently authenticated user from the current JWT claim.
 */
val PipelineContext<*, ApplicationCall>.requireUserEmail: String
    get() = call.principal<JWTPrincipal>()!!.payload.getClaim(JwtUtil.CLAIM_KEY_EMAIL).asString()
