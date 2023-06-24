package dev.maneki.plugins


import dev.maneki.di.appModule
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.installKoin() {
    install(Koin) {
        slf4jLogger()
        modules(appModule(this@installKoin))
    }
}
