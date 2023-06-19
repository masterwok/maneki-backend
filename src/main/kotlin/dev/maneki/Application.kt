package dev.maneki

import dev.maneki.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(
        Netty,
        port = 80,
        host = "0.0.0.0",
        module = Application::module,
    ).start(wait = true)

}

fun Application.module() {
    configureCORS()
    configureKoin()
    configureSecurity()
    configureMonitoring()
    configureContentNegotiation()
    configureRouting()
}

