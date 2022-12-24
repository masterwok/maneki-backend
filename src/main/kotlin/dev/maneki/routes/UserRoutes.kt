package dev.maneki.routes

import dev.maneki.dtos.UserDto
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.datetime.Clock

fun Route.userRouting() {
    route("user") {
        get {
            call.respond(
                listOf(
                    UserDto(
                        "Jonathan.Trowbridge@gmail.com",
                        "Jonathan",
                        "Trowbridge",
                        Clock.System.now(),
                        Clock.System.now(),
                    )
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
