package dev.maneki.features.authentication

import dev.maneki.dtos.ApiErrorResponse
import dev.maneki.features.authentication.dtos.*
import domain.models.Token
import domain.usecases.Login
import domain.usecases.RefreshAuthToken
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.authRouting() {
    route("/auth") {
        refreshRoute()
        loginRoute()
    }
}

fun Route.loginRoute() {
    val login by inject<Login>()

    /**
     * Login to the application.
     */
    post {
        val model = call.receive<LoginRequestDto>().toLoginModel()

        when (val result = login(model)) {
            null -> call.respond(HttpStatusCode.Unauthorized, ApiErrorResponse("Unknown username or password."))
            else -> call.respond(HttpStatusCode.OK, LoginResponseDto.from(result))
        }
    }
}

fun Route.refreshRoute() {
    val refreshAuthToken by inject<RefreshAuthToken>()

    /**
     * Refresh the authentication access token.
     */
    post("refresh") {
        val refreshToken = call.receive<RefreshTokenRequestDto>().refreshToken

        val result = try {
            refreshAuthToken(refreshToken)
        } catch (ex: Exception) {
            ex
        }

        when (result) {
            is Token -> call.respond(HttpStatusCode.OK, LoginResponseDto.from(result))
            is Exception -> {
                call.respond(
                    HttpStatusCode.Unauthorized,
                    ApiErrorResponse(result.message ?: "Unauthorized. Unable to complete your request.")
                )
            }
        }
    }
}
