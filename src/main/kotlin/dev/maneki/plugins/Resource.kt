package dev.maneki.plugins

import io.ktor.server.application.*
import io.ktor.server.resources.*


fun Application.configureResources() {
    install(Resources)
}
