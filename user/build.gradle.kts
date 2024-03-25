plugins {
    kotlin("jvm")
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
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}