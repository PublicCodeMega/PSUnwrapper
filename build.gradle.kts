plugins {
    kotlin("jvm") version "2.2.20"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "c.PSUnwrapper"
version = "1.0"
//PSUnwrapper-1.0-all.jar
repositories {
    mavenCentral()
}


dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

tasks.shadowJar {
    mergeServiceFiles()
    manifest {
        attributes["Main-Class"] = "c.PSUnwrapper.MainKt"
    }
}