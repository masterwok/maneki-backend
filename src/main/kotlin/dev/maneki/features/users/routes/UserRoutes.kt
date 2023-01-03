package dev.maneki.features.users.routes

import dev.maneki.dtos.*
import dev.maneki.extensions.requireUserEmail
import dev.maneki.features.users.dtos.CreateUserDto
import dev.maneki.features.users.dtos.UserDto
import dev.maneki.features.users.dtos.from
import dev.maneki.features.users.dtos.toCreateUserModel
import features.users.models.User
import features.users.repositories.exceptions.CreateUserUnknownException
import features.users.repositories.exceptions.UserAlreadyExistsException
import features.users.usecases.CreateUser
import features.users.usecases.QueryUserByEmail
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.flow.firstOrNull
import org.koin.ktor.ext.inject

fun Route.userRouting() {
    route("user") {
        getUserInfoRoute()
        createUserRoute()
    }
}

fun Route.getUserInfoRoute() {
    val queryUserByEmail by inject<QueryUserByEmail>()

    authenticate {
        get {
            when (val result = queryUserByEmail(requireUserEmail).firstOrNull()) {
                is User -> call.respond(ApiResponse.success(UserDto.from(result)))
                null -> call.respond(HttpStatusCode.NotFound, ApiResponse.error("User not found"))
            }
        }
    }
}

fun Route.createUserRoute() {
    val createUser by inject<CreateUser>()
    post {
        val model = call.receive<CreateUserDto>().toCreateUserModel()

        val result = try {
            createUser(model)
        } catch (ex: UserAlreadyExistsException) {
            ex
        } catch (ex: CreateUserUnknownException) {
            ex
        }

        when (result) {
            is User -> call.respond(ApiResponse.success(UserDto.from(result)))
            is UserAlreadyExistsException -> call.respond(
                HttpStatusCode.Conflict,
                ApiResponse.error(result.message),
            )

            is CreateUserUnknownException -> call.respond(
                HttpStatusCode.InternalServerError,
                ApiResponse.error(result.message),
            )
        }
    }
}
