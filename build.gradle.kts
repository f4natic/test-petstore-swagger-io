plugins {
    id("java")
}

group = "io.swagger.petstore"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.testng:testng:7.8.0")
    testImplementation("io.rest-assured:rest-assured:5.3.2")
}

tasks.getByName<Test>("test") {
    useTestNG()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17));
    }
}