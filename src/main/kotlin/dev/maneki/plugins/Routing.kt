package dev.maneki.plugins

import dev.maneki.routes.playerRouting
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*

fun Application.configureRouting() {

    routing {
        playerRouting()
    }

    routing {
        get("/") {
            call.respondText("Hello World!")
        }
    }
}
