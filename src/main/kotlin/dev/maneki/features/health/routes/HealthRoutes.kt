package dev.maneki.features.health.routes

import dev.maneki.dtos.ApiResponse
import dev.maneki.dtos.success
import dev.maneki.features.authentication.dtos.Foo
import dev.maneki.features.authentication.dtos.FooData
import dev.maneki.features.authentication.dtos.LoginResponseDto
import dev.maneki.features.authentication.dtos.from
import io.ktor.http.*
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

    get("derp") {
//        call.respond("OK")
        call.respond(HttpStatusCode.OK, Foo(data = FooData(name = "Jonathan")))
    }
}
