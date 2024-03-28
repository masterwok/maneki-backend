val kotlin_version: String by project
val ktor_version: String by project
val sqldelight_version: String by project
val mysql_connector_java_version: String by project
val kotlinx_coroutines_core_version: String by project
val koin_version: String by project

plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.20"
}

group = "dev.maneki"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":core"))
    implementation(project(":data"))

    testImplementation("org.jetbrains.kotlin:kotlin-test")

    // DateTimes
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinx_coroutines_core_version")

    implementation("app.cash.sqldelight:coroutines-extensions:$sqldelight_version")

    implementation("io.insert-koin:koin-ktor:$koin_version")

    implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktor_version")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}