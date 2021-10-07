import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10"
}

group = "me.bocek"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(group = "io.javalin", name = "javalin", version = "4.1.0")

    implementation(group = "org.slf4j", name = "slf4j-simple", version = "1.7.32")
    implementation(group = "com.github.mifmif", name = "generex", version = "1.0.2")
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "13"
}