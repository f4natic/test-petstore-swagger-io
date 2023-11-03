plugins {
    id("java")
}

group = "io.swagger.petstore"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.testng:testng:6.14.3")
}

tasks.getByName<Test>("test") {
    useTestNG()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11));
    }
}