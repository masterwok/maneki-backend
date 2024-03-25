plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "maneki-backend"
include("data")
include("core")
include("user")
include("authentication")
