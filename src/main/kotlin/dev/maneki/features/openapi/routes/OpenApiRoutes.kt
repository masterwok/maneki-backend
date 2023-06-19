package dev.maneki.features.swagger.routes

import io.ktor.server.plugins.openapi.*
import io.ktor.server.routing.*

fun Routing.openApiRouting() {
    openAPI(
        path = "openapi",
        swaggerFile = "openapi/documentation.yaml",
    )
}
