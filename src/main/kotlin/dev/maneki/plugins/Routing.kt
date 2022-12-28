package dev.maneki.plugins

import dev.maneki.features.authentication.authRouting
import dev.maneki.routes.userRouting
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        userRouting()
        authRouting()
    }
}
