package dev.maneki.features.example

import dev.maneki.dtos.ApiResponse
import dev.maneki.dtos.success
import dev.maneki.features.authentication.dtos.LoginResponseDto
import dev.maneki.features.authentication.dtos.from
import dev.maneki.features.health.routes.pingRoute
import io.ktor.http.*
import io.ktor.resources.*
import io.ktor.server.application.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.routing.get
import kotlinx.serialization.Serializable


@Serializable
@Resource("/articles")
class Articles(val sort: String? = "new") {
    @Serializable
    @Resource("new")
    class New(val parent: Articles = Articles())

    @Serializable
    @Resource("{id}")
    class Id(val parent: Articles = Articles(), val id: Long) {
        @Serializable
        @Resource("edit")
        class Edit(val parent: Id)
    }
}

fun Route.exampleRouting() {
    get<Articles> { article ->
        // Get all articles ...
        call.respondText("List of articles sorted starting from ${article.sort}")
//        call.respond()
        call.respond(HttpStatusCode.OK, ApiResponse.success<LoginResponseDto>(LoginResponseDto("token", "refreh")))

    }
    get<Articles.New> {
        // Show a page with fields for creating a new article ...
        call.respondText("Create a new article")
    }
//    post<Articles> {
//        // Save an article ...
//        call.respondText("An article is saved", status = HttpStatusCode.Created)
//    }

    get<Articles.Id> { article ->
        // Show an article with id ${article.id} ...
        call.respondText("An article with id ${article.id}", status = HttpStatusCode.OK)
    }
    get<Articles.Id.Edit> { article ->
        // Show a page with fields for editing an article ...
        call.respondText("Edit an article with id ${article.parent.id}", status = HttpStatusCode.OK)
    }

//    put<Articles.Id> { article ->
//        // Update an article ...
//        call.respondText("An article with id ${article.id} updated", status = HttpStatusCode.OK)
//    }

    delete<Articles.Id> { article ->
        // Delete an article ...
        call.respondText("An article with id ${article.id} deleted", status = HttpStatusCode.OK)
    }
}
