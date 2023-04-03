package common.extensions

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcDriver
import com.squareup.sqldelight.sqlite.driver.asJdbcDriver
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import common.adapters.TimestampColumnAdapter
import dev.maneki.data.Database
import dev.maneki.data.Refresh_token
import dev.maneki.data.User
import io.ktor.server.application.*

private const val JDBC_MYSQL = "mysql"
private const val JDBC_MARIADB = "mariadb"
private const val DRIVER_CLASS_MYSQL = "com.mysql.cj.jdbc.Driver"
private const val DRIVER_CLASS_MARIADB = "org.mariadb.jdbc.Driver"

fun Database.Companion.init(app: Application): Database {
    val driver = createMySqlDatabaseDriver().apply {
        app.environment.monitor.subscribe(ApplicationStopped) { close() }
    }

    Schema.migrateIfRequired(driver)

    return Database(
        driver,
        refresh_tokenAdapter = Refresh_token.Adapter(
            expires_onAdapter = TimestampColumnAdapter,
        ),
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

private fun getDriverNameByJdbcUrl(jdbcUrl: String): String = when {
    jdbcUrl.contains(JDBC_MYSQL) -> DRIVER_CLASS_MYSQL
    jdbcUrl.contains(JDBC_MARIADB) -> DRIVER_CLASS_MARIADB
    else -> throw IllegalArgumentException("Failed to initialize database driver: JDBC_URL environment variable must be either mysql or mariadb")
}

private fun createMySqlDatabaseDriver(): JdbcDriver {
    val jdbcUrl = System.getenv("JDBC_URL").apply {
        if (isNullOrEmpty()) throw IllegalArgumentException("Failed to initialize database driver: JDBC_URL environment variable required (SQLite or MySQL).")
    }

    return HikariDataSource(HikariConfig().apply {
        this.jdbcUrl = jdbcUrl
        username = System.getenv("MYSQL_USER").apply {
            if (isNullOrEmpty()) throw IllegalArgumentException("Failed to initialize MySQL driver: MYSQL_USER environment variable required.")
        }
        password = System.getenv("MYSQL_PASSWORD").apply {
            if (isNullOrEmpty()) throw IllegalArgumentException("Failed to initialize MySQL driver: MYSQL_USER environment variable required.")
        }
        this.driverClassName = getDriverNameByJdbcUrl(jdbcUrl)
        connectionTestQuery = "SELECT 1"
        poolName = "MySqlPool"
        maximumPoolSize = 50
        maxLifetime = 60000
        idleTimeout = 45000
    }).asJdbcDriver()
}
