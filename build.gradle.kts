plugins {
    java
    application
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

application {
    mainClass.set("registration.Main")
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}
