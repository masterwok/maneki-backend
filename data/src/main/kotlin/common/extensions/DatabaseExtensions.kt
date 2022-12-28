package dev.maneki.common.extensions

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcDriver
import com.squareup.sqldelight.sqlite.driver.asJdbcDriver
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import common.adapters.TimestampColumnAdapter
import dev.maneki.data.Database
import dev.maneki.data.User
import io.ktor.server.application.*


fun Database.Companion.init(app: Application): Database {
    val driver = createMySqlDatabaseDriver().apply {
        app.environment.monitor.subscribe(ApplicationStopped) { close() }
    }

    Schema.migrateIfRequired(driver)

    return Database(
        driver,
        userAdapter = User.Adapter(
            created_onAdapter = TimestampColumnAdapter,
            updated_onAdapter = TimestampColumnAdapter,
        )
    )
}

private fun SqlDriver.Schema.migrateIfRequired(driver: JdbcDriver) {
    try {
        val currentSchemaVersion = getCurrentSchemaVersion(driver)

        if (currentSchemaVersion < version) {
            Database.Schema.migrate(driver, currentSchemaVersion, version)
        }
    } catch (_: Exception) {
        Database.Schema.migrate(driver, 1, version)
    }
}

private fun getCurrentSchemaVersion(driver: JdbcDriver): Int = driver.let {
    driver.executeQuery(
        null,
        "SELECT version FROM schema_version ORDER BY version DESC LIMIT 1;",
        0,
        null,
    ).use {
        it.next()
        it.getLong(0)!!.toInt()
    } + 1
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
