val ktor_version: String by project
val kotlin_version: String by project
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
    implementation(project(":authentication"))

    testImplementation("org.jetbrains.kotlin:kotlin-test")

    // DateTimes
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")

    implementation("io.insert-koin:koin-ktor:$koin_version")
    implementation("io.ktor:ktor-server-auth-jvm:$ktor_version")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}