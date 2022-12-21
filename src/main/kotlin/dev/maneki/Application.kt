package dev.maneki

//import com.squareup.sqldelight.gradle.SqlDelightDatabase
//import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import dev.maneki.plugins.*
//import sqldelight.com.squareup.sqlite.migrations.Database

fun main() {
    embeddedServer(
        Netty,
        port = 8080,
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
}


fun Application.foo() {
//    val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)


//    val foo = MyDatabase(driver)
//
//    val playerQueries = foo.playerQueries
}
