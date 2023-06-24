package dev.maneki.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import dev.maneki.utils.JwtUtil
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.installSecurity() {

    authentication {
        jwt {
            verifier(
                JWT
                    .require(Algorithm.HMAC256(JwtUtil.secret))
                    .build()
            )

            validate { credential -> JWTPrincipal(credential.payload) }
        }
    }
}
