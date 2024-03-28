package common.extensions

import app.cash.sqldelight.db.AfterVersion
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.jdbc.asJdbcDriver
import app.cash.sqldelight.driver.jdbc.JdbcDriver;
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
    val mysqlDriver = createMySqlDatabaseDriver().apply {
        app.environment.monitor.subscribe(ApplicationStopped) { close() }
    }

    Schema.migrateIfRequired(mysqlDriver)

    return Database(
        mysqlDriver,
        refresh_tokenAdapter = Refresh_token.Adapter(
            expires_onAdapter = TimestampColumnAdapter,
        ),
        userAdapter = User.Adapter(
            created_onAdapter = TimestampColumnAdapter,
            updated_onAdapter = TimestampColumnAdapter,
        )
    )
}

private fun SqlSchema<QueryResult.Value<Unit>>.migrateIfRequired(driver: JdbcDriver) {
    try {
        val currentSchemaVersion = getCurrentSchemaVersion(driver)

        if (currentSchemaVersion < version) {
            Database.Schema.migrate(driver, currentSchemaVersion, version)
        }
    } catch (_: Exception) {
        Database.Schema.migrate(driver, 1, version)
    }
}

private fun getCurrentSchemaVersion(driver: JdbcDriver): Long = driver.executeQuery(
    null,
    "SELECT version FROM schema_version ORDER BY version DESC LIMIT 1;",
    { cursor ->
        cursor.next()

        val version = cursor.getLong(0) ?: 0

        QueryResult.Value(version + 1)
    },
    0,
    null,
).value

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
