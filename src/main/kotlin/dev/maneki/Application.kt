package dev.maneki

import dev.maneki.common.extensions.init
import dev.maneki.plugins.configureHTTP
import dev.maneki.plugins.configureMonitoring
import dev.maneki.plugins.configureRouting
import dev.maneki.plugins.configureSerialization
import dev.maneki.utils.HashUtil
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import java.util.*

fun main() {
    embeddedServer(
        Netty, port = 8080, host = "0.0.0.0", module = Application::module
    ).start(wait = true)
}

fun Application.module() {
//    configureSecurity()
    configureHTTP()
    configureMonitoring()
    configureSerialization()
    configureRouting()

    val database = Database.init(this)

    val password = "TestOn1="

    val user = database.userQueries.selectById(1).executeAsOne()

    val allUsers = database.userQueries.selectAllUsers().executeAsList()

    val isValidPassword = HashUtil.verifyPassword(password, user.password)
    val foo = HashUtil.verifyPassword("invalidPassword", user.password)

    val x = 1

}

