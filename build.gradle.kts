import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.71"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    testImplementation("org.junit.jupiter:junit-jupiter:5.6.2")
}

tasks {
    test {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }

    withType<KotlinCompile>().configureEach {
        kotlinOptions {
            freeCompilerArgs = listOf("-XXLanguage:+InlineClasses")
            // config JVM target to 1.8 to prevent from error:
            //     Cannot inline bytecode built with JVM target 1.8 into bytecode that is being built with JVM target 1.6. Please specify proper '-jvm-target' option
            jvmTarget = "1.8"
        }
    }
}
