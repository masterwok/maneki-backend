package dev.maneki.routes

import dev.maneki.dtos.CreateUserDto
import dev.maneki.dtos.UserDto
import dev.maneki.dtos.from
import dev.maneki.dtos.toCreateUserModel
import features.users.usecases.CreateUserUseCase
import features.users.usecases.QueryUserByIdUseCase
import features.users.usecases.QueryUsersUseCase
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.flow.first
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
            val id = call.parameters["id"]!!.toInt()

            val user = queryUserById(id).first()

            call.respond(UserDto.from(user))
        }
        post {
            val model = call.receive<CreateUserDto>().toCreateUserModel()

            val user = createUser(model)

            call.respond(UserDto.from(user))
        }
        delete("{id?}") {

        }
    }

}
