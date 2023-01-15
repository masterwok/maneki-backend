package dev.maneki.features.health.routes

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.healthRouting() {
    route("health") {
        pingRoute()
    }
}

fun Route.pingRoute() {
    get("status") {
        call.respond("OK")
    }
}
