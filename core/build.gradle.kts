val kotlin_version: String by project
val sqldelight_version: String by project
val mysql_connector_java_version: String by project
val kotlinx_coroutines_core_version: String by project

plugins {
    kotlin("jvm")
}

group = "dev.maneki"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
