val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val sqldelight_version: String by project
val mysql_connector_java_version: String by project

plugins {
    kotlin("jvm") version "1.7.20"
    id("io.ktor.plugin") version "2.2.1"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.20"
    id("com.squareup.sqldelight") version "1.5.4"
}

group = "dev.maneki"
version = "0.0.1"
application {
    mainClass.set("dev.maneki.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

sqldelight {
    database("Database") {
        dialect = "mysql"
        packageName = "dev.maneki"
        deriveSchemaFromMigrations = true
    }
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-auth-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-swagger:$ktor_version")
    implementation("io.ktor:ktor-server-call-logging-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")

    // Force coroutines version
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    // JDBC driver
    implementation("com.squareup.sqldelight:jdbc-driver:$sqldelight_version")
    runtimeOnly("mysql:mysql-connector-java:$mysql_connector_java_version")

    // Hikari JDBC connection pool
    implementation("com.zaxxer:HikariCP:5.0.1")

    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}
