val kotlin_version: String by project
val sqldelight_version: String by project
val mysql_connector_java_version: String by project
val kotlinx_coroutines_core_version: String by project

plugins {
    kotlin("jvm") version "1.7.20"
}

group = "dev.maneki"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":core"))

    // Force coroutines version
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinx_coroutines_core_version")

    // DateTimes
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
