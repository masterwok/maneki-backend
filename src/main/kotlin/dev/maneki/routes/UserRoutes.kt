package dev.maneki.routes

import dev.maneki.dtos.CreateUserDto
import dev.maneki.dtos.UserDto
import dev.maneki.dtos.from
import dev.maneki.dtos.toCreateUserModel
import features.users.models.User
import features.users.repositories.exceptions.CreateUserUnknownException
import features.users.repositories.exceptions.UserAlreadyExistsException
import features.users.usecases.CreateUserUseCase
import features.users.usecases.QueryUserByIdUseCase
import features.users.usecases.QueryUsersUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import org.koin.ktor.ext.inject

fun Route.userRouting() {
    val createUser by inject<CreateUserUseCase>()
    val queryUserById by inject<QueryUserByIdUseCase>()
    val queryUsers by inject<QueryUsersUseCase>()

    route("user") {
        get {
            call.respond(queryUsers().first().map(UserDto::from))
        }
        get("{id?}") {
            val id = call.parameters["id"]?.toInt()

            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid id parameter")
                return@get
            }

            when (val result = queryUserById(id).firstOrNull()) {
                is User -> call.respond(HttpStatusCode.Created, UserDto.from(result))
                null -> call.respond(HttpStatusCode.NotFound, "User not found")
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
                is User -> call.respond(UserDto.from(result))
                is UserAlreadyExistsException -> call.respond(HttpStatusCode.Conflict, result.message)
                is CreateUserUnknownException -> call.respond(HttpStatusCode.InternalServerError, result.message)
            }
        }
        delete("{id?}") {

        }
    }

}
