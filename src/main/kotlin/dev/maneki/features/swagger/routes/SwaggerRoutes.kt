package dev.maneki.features.swagger.routes

import io.ktor.server.plugins.swagger.*
import io.ktor.server.routing.*

fun Routing.swaggerRouting() {
    swaggerUI(
        path = "swagger",
        swaggerFile = "openapi/documentation.yaml",
    )
}
