package dev.maneki.features.swagger.routes

import io.ktor.server.plugins.openapi.*
import io.ktor.server.routing.*
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.servers.Server

fun Routing.openApiRouting() {
    openAPI(
        path = "openapi",
        swaggerFile = "openapi/documentation.yaml",
    ) {
        opts.openAPI.apply {
            info.apply {
                title = "Maneki API"
                description = "The OpenAPI specification for the Maneki API."
                version = "1.0.0"
                contact = Contact().apply {
                    name = "Jonathan Trowbridge"
                    email = "jonathan.trowbridge@gmail.com"
                    url = "home.maneki.dev"
                }
            }
            servers = listOf(
                Server().apply {
                    description = "Localhost"
                    url = "http://localhost:80"
                },
                Server().apply {
                    description = "Development"
                    url = "http://home.maneki.dev:80"
                }
            )
        }
    }
}
