package dev.maneki.routes

import dev.maneki.dtos.*
import features.users.models.User
import features.users.repositories.exceptions.CreateUserUnknownException
import features.users.repositories.exceptions.UserAlreadyExistsException
import features.users.usecases.CreateUser
import features.users.usecases.QueryUserById
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.flow.firstOrNull
import org.koin.ktor.ext.inject

fun Route.userRouting() {
    val createUser by inject<CreateUser>()
    val queryUserById by inject<QueryUserById>()

    route("user") {
        authenticate {
            get("{id?}") {
                val id = call.parameters["id"]?.toIntOrNull()

                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, ApiResponse.error("Invalid id parameter"))
                    return@get
                }

                when (val result = queryUserById(id).firstOrNull()) {
                    is User -> call.respond(ApiResponse.success(UserDto.from(result)))
                    null -> call.respond(HttpStatusCode.NotFound, ApiResponse.error("User not found"))
                }
            }
        }
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

}
