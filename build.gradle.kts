plugins {
    id("java-library")
    id("xyz.jpenilla.run-paper") version "2.3.1"
    id("maven-publish")
}

afterEvaluate {
    publishing{
        publications{
            create<MavenPublication>("mavenJava"){
                from(components["java"])
                groupId = "com.github.manuxs"
                artifactId = "eventutils"
                version = "1.0.0"
            }
        }
    }
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.extendedclip.com/releases/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.11.7")
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(21)
}

tasks {
    runServer {
        // Configure the Minecraft version for our task.
        // This is the only required configuration besides applying the plugin.
        // Your plugin's jar (or shadowJar if present) will be used automatically.
        minecraftVersion("1.21.1")
        jvmArgs("-Xms2G", "-Xmx2G")
    }

    processResources {
        val props = mapOf("version" to version)
        filesMatching("plugin.yml") {
            expand(props)
        }
    }
}
