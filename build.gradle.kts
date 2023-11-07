import io.qameta.allure.gradle.base.AllureExtension
import io.qameta.allure.gradle.report.tasks.AllureServe

plugins {
    id("java")
    id("io.qameta.allure") version "2.11.2"
}

group = "io.swagger.petstore"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("com.fasterxml.jackson.core:jackson-databind:2.15.3")
    testImplementation("org.testng:testng:7.8.0")
    testImplementation("io.rest-assured:rest-assured:5.3.2")
}

tasks.getByName<Test>("test") {
    useTestNG() {
        suites("src/test/resources/testng.xml")
    }
}

allure {
    tasks.withType<AllureServe>().configureEach {
        port.set(8080)
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17));
    }
}

allprojects {
    tasks.withType<JavaCompile>().configureEach {
        options.encoding = "UTF-8"
    }
}