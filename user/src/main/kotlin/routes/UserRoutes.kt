package routes

import extensions.requireUserEmail
import domain.models.User
import domain.repositories.exceptions.CreateUserUnknownException
import domain.repositories.exceptions.UserAlreadyExistsException
import domain.usecases.CreateUser
import domain.usecases.QueryUserByEmail
import domain.usecases.QueryUsers
import dtos.ApiErrorResponse
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import routes.dtos.*

fun Route.userRouting() {
    route("user") {
        getUserInfoRoute()
        createUserRoute()
    }

    route("users") {
        queryUsersRoute()
    }
}

fun Route.queryUsersRoute() {
    val queryUsers by inject<QueryUsers>()

    get {
        val result = queryUsers().map(UserDto::from)

        call.respond(UsersResponseDto(users = result))
    }


//    authenticate {
//        /**
//         * Get the user information of the user.
//         */
//        get {
//            when (val result = queryUserByEmail(requireUserEmail)) {
//                is User -> call.respond(UserDto.from(result))
//                null -> call.respond(HttpStatusCode.NotFound, ApiErrorResponse("User not found"))
//            }
//        }
//    }
}

fun Route.getUserInfoRoute() {
    val queryUserByEmail by inject<QueryUserByEmail>()

    authenticate {
        /**
         * Get the user information of the user.
         */
        get {
            when (val result = queryUserByEmail(requireUserEmail)) {
                is User -> call.respond(UserDto.from(result))
                null -> call.respond(HttpStatusCode.NotFound, ApiErrorResponse("User not found"))
            }
        }
    }
}

fun Route.createUserRoute() {
    val createUser by inject<CreateUser>()

    /**
     * Create a new user account.
     */
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
            is UserAlreadyExistsException -> call.respond(
                HttpStatusCode.Conflict,
                ApiErrorResponse(result.message),
            )

            is CreateUserUnknownException -> call.respond(
                HttpStatusCode.InternalServerError,
                ApiErrorResponse(result.message),
            )
        }
    }
}
