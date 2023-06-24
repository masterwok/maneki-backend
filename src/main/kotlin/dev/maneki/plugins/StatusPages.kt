package dev.maneki.plugins

import dev.maneki.dtos.ApiResponse
import dev.maneki.dtos.error
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.installStatusPages() {
    install(StatusPages) {
        status(HttpStatusCode.Unauthorized) { call, status ->
            call.respond(status, ApiResponse.error("Unauthorized. Unable to complete your request."))
        }

        status(HttpStatusCode.BadRequest) { call, status ->
            call.respond(status, ApiResponse.error("Invalid request. Please check your request and try again."))
        }

        status(HttpStatusCode.InternalServerError) { call, status ->
            call.respond(status, ApiResponse.error("Internal server error. Please try again later."))
        }
    }
}
