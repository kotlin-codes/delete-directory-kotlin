import Build_gradle.Constants.commons_lang3_version
import Build_gradle.Constants.defaultTaskName
import Build_gradle.Constants.jvmTargetVersion
import Build_gradle.Constants.kotlinCompilerOptions
import Build_gradle.Constants.kotlin_logging_version
import Build_gradle.Constants.mainClass
import Build_gradle.Constants.projectArtifactGroup
import Build_gradle.Constants.projectVersion
import Build_gradle.Constants.slf4j_simple_version
import Build_gradle.Constants.unix4j_command_version
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

object Constants {
    const val projectArtifactGroup = "com.cheroliv.misc"
    const val projectVersion = "0.0.1"
    const val mainClass = "com.cheroliv.util.AppKt"
    const val defaultTaskName = "run"
    const val jvmTargetVersion = "1.8"
    const val kotlinCompilerOptions = "-Xjsr305=strict"
    const val kotlin_logging_version = "1.7.8"
    const val slf4j_simple_version = "1.7.26"
    const val unix4j_command_version = "0.5"
    const val commons_lang3_version = "3.11"
}


plugins {
    id("org.jetbrains.kotlin.jvm") version "1.4.10"
    // Apply the application plugin to add support for building a CLI application.
    application
}

repositories {
    jcenter()
}

group = projectArtifactGroup
version = projectVersion

java.sourceCompatibility = JavaVersion.VERSION_1_8

defaultTasks(defaultTaskName)


dependencies {
    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    // Use the Kotlin JDK 8 standard library.
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    // Logger
    implementation("io.github.microutils:kotlin-logging:$kotlin_logging_version")
    implementation("org.slf4j:slf4j-simple:$slf4j_simple_version")
    // Use the Kotlin test library.
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    // Use the Kotlin JUnit integration.
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    // Unix command
    implementation("org.unix4j:unix4j-command:$unix4j_command_version")
    // Apache Common to handle string
    implementation("org.apache.commons:commons-lang3:$commons_lang3_version")
}


tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf(kotlinCompilerOptions)
        jvmTarget = jvmTargetVersion
    }
}

application {
    // Define the main class for the application.
    mainClassName = Constants.mainClass
}

project.tasks.jar<Jar?> {
    this?.manifest?.attributes?.set("Main-Class", mainClass)
    this?.doLast { println("It happens just after jar tasks!") }
}