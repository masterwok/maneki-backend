package dev.maneki.plugins

import dev.maneki.features.authentication.authRouting
import dev.maneki.features.health.routes.healthRouting
import dev.maneki.features.users.routes.userRouting
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.installRouting() {
    routing {
        healthRouting()
        userRouting()
        authRouting()
    }

}
