package dev.maneki.plugins

import dev.maneki.routes.userRouting
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*

fun Application.configureRouting() {

    routing {
        userRouting()
    }

    routing {
        get("/") {
            call.respondText("Hello World!")
        }
    }
}
