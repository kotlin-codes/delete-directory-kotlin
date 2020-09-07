plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm") version "1.4.0"

    // Apply the application plugin to add support for building a CLI application.
    application
}

repositories {
    jcenter()
}

dependencies {
    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    // Use the Kotlin JDK 8 standard library.
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // Logger
    implementation("io.github.microutils:kotlin-logging:1.7.8")
    implementation("org.slf4j:slf4j-simple:1.7.26")

    // Use the Kotlin test library.
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    // Use the Kotlin JUnit integration.
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")

    // Unix command
    implementation("org.unix4j:unix4j-command:0.5")
}

application {
    // Define the main class for the application.
    mainClassName = "com.cheroliv.util.AppKt"
}
//copy jar to project root directory
// to execute from project root directory
project.tasks {
    jar {
        doLast {
            File("build/libs/deleteDir.jar").copyTo(File("deleteDir.jar"), true)
        }
    }
}