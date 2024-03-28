package dev.maneki.plugins

import routes.authRouting
import dev.maneki.features.health.routes.healthRouting
import routes.userRouting
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.installRouting() {
    routing {
        healthRouting()
        userRouting()
        authRouting()
    }

}
