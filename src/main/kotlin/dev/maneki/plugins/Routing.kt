package dev.maneki.plugins

import dev.maneki.features.authentication.authRouting
import dev.maneki.features.health.routes.healthRouting
import dev.maneki.features.swagger.routes.openApiRouting
import dev.maneki.features.swagger.routes.swaggerRouting
import dev.maneki.features.users.routes.userRouting
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        healthRouting()
        userRouting()
        authRouting()
        swaggerRouting()
        openApiRouting()
    }

}
