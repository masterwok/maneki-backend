package dev.maneki.routes

import dev.maneki.dtos.PlayerDto
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.playerRouting() {
    route("player") {
        get {
            call.respond(
                listOf(
                    PlayerDto(1, "Jonathan Trowbridge")
                )
            )
        }
        get("{id?}") {

        }
        post {

        }
        delete("{id?}") {

        }
    }

}
