package dev.maneki

import dev.maneki.common.extensions.init
import dev.maneki.plugins.configureHTTP
import dev.maneki.plugins.configureMonitoring
import dev.maneki.plugins.configureRouting
import dev.maneki.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(
        Netty, port = 8080,
        host = "0.0.0.0",
        module = Application::module
    ).start(wait = true)
}

fun Application.module() {
//    configureSecurity()
    configureHTTP()
    configureMonitoring()
    configureSerialization()
    configureRouting()

    val database = Database.init(this)

}

