val ktor_version: String by project
val kotlin_version: String by project
val sqldelight_version: String by project
val mysql_connector_java_version: String by project
val kotlinx_coroutines_core_version: String by project

plugins {
    kotlin("jvm") version "1.9.23"
    id("app.cash.sqldelight") version "2.0.1"
}


group = "dev.maneki.data"
version = "0.0.1"

repositories {
    mavenCentral()
}

sqldelight {
    databases {
        create("Database") {
            dialect("app.cash.sqldelight:mysql-dialect:2.0.1")
            packageName.set("dev.maneki.data")
            deriveSchemaFromMigrations.set(true)
        }
    }
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinx_coroutines_core_version")

    implementation("app.cash.sqldelight:coroutines-extensions:$sqldelight_version")

    // DateTimes
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")

    // MySQL JDBC driver
    implementation("app.cash.sqldelight:jdbc-driver:$sqldelight_version")
    runtimeOnly("mysql:mysql-connector-java:$mysql_connector_java_version")

    // MariaDB JDBC driver
    implementation("org.mariadb.jdbc:mariadb-java-client:2.7.4")

    // Hikari JDBC connection pool
    implementation("com.zaxxer:HikariCP:5.0.1")

    // Encryption for passwords
    implementation("at.favre.lib:bcrypt:0.9.0")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

