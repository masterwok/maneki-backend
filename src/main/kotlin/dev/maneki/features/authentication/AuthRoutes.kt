package dev.maneki.features.authentication

import dev.maneki.dtos.ApiResponse
import dev.maneki.dtos.error
import dev.maneki.dtos.success
import dev.maneki.features.authentication.dtos.LoginRequestDto
import dev.maneki.features.authentication.dtos.LoginResponseDto
import dev.maneki.features.authentication.dtos.from
import dev.maneki.features.authentication.dtos.toLoginModel
import features.authentication.usecases.Login
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.authRouting() {
    val login by inject<Login>()

    route("/auth") {

        get("refresh") {
            // Handle GET request to /users/{id}
        }
        post {
            val model = call.receive<LoginRequestDto>().toLoginModel()

            when (val token = login(model)) {
                null -> call.respond(HttpStatusCode.Unauthorized, ApiResponse.error("Unknown username or password."))
                else -> call.respond(HttpStatusCode.OK, ApiResponse.success(LoginResponseDto.from(token)))
            }
        }
    }
}
