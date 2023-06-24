package dev.maneki.plugins

import io.ktor.server.plugins.callloging.*
import org.slf4j.event.*
import io.ktor.server.request.*
import io.ktor.server.application.*

fun Application.installMonitoring() {
    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }

}
