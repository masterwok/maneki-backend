package dev.maneki.plugins

import dev.maneki.dtos.ApiErrorResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.installStatusPages() {
    install(StatusPages) {
        status(HttpStatusCode.Unauthorized) { call, status ->
            if (call.response.status() == null) {
                call.respond(status, ApiErrorResponse("Unauthorized. Unable to complete your request."))
            }
        }

        status(HttpStatusCode.BadRequest) { call, status ->
            if (call.response.status() == null) {
                call.respond(status, ApiErrorResponse("Invalid request. Please check your request and try again."))
            }
        }

        status(HttpStatusCode.InternalServerError) { call, status ->
            if (call.response.status() == null) {
                call.respond(status, ApiErrorResponse("Internal server error. Please try again later."))
            }
        }

        exception<Throwable> { call, cause ->
            if (call.response.status() == null) {
                call.respond(
                    HttpStatusCode.InternalServerError,
                    ApiErrorResponse("Internal server error. Please try again later."),
                )
            }
        }
    }
}
