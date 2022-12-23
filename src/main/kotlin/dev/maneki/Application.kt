package dev.maneki

import com.squareup.sqldelight.sqlite.driver.JdbcDriver
import com.squareup.sqldelight.sqlite.driver.asJdbcDriver
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import dev.maneki.plugins.configureHTTP
import dev.maneki.plugins.configureMonitoring
import dev.maneki.plugins.configureRouting
import dev.maneki.plugins.configureSerialization
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import java.lang.Exception

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


    val driver = createMySqlDatabaseDriver().apply {
        environment.monitor.subscribe(ApplicationStopped) { close() }
    }

    val database = Database.init(this)

    val users = database.databaseQueries.selectAllUsers().executeAsList()
    val foos = database.databaseQueries.selectAllFoo().executeAsList()

    val x = 1

}

fun Database.Companion.init(app: Application): Database {
    val driver = createMySqlDatabaseDriver().apply {
        app.environment.monitor.subscribe(ApplicationStopped) { close() }
    }

    val schemaVersion = Schema.version

    try {
        val currentSchemaVersion = driver.executeQuery(
            null,
            "SELECT version FROM schema_version ORDER BY version DESC LIMIT 1;",
            0,
            null,
        ).use {
            it.next()
            it.getLong(0)!!.toInt()
        } + 1

        if (currentSchemaVersion < schemaVersion) {
            Schema.migrate(driver, currentSchemaVersion, schemaVersion)
        }
    } catch (ex: Exception) {
        Schema.migrate(driver, 1, schemaVersion)
    }

    return Database(driver)
}


private fun createMySqlDatabaseDriver(): JdbcDriver {
    return HikariDataSource(HikariConfig().apply {
        jdbcUrl = System.getenv("JDBC_URL").apply {
            if (isNullOrEmpty()) throw IllegalArgumentException("Failed to initialize database driver: JDBC_URL environment variable required (SQLite or MySQL).")
        }
        username = System.getenv("MYSQL_USER").apply {
            if (isNullOrEmpty()) throw IllegalArgumentException("Failed to initialize MySQL driver: MYSQL_USER environment variable required.")
        }
        password = System.getenv("MYSQL_PASSWORD").apply {
            if (isNullOrEmpty()) throw IllegalArgumentException("Failed to initialize MySQL driver: MYSQL_USER environment variable required.")
        }
        driverClassName = "com.mysql.jdbc.Driver"
        connectionTestQuery = "SELECT 1"
        poolName = "MySqlPool"
        maximumPoolSize = 50
        maxLifetime = 60000
        idleTimeout = 45000
    }).asJdbcDriver()
}
