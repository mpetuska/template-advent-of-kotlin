plugins {
    kotlin("jvm") version "1.8.10"
    id("com.github.jakemarsden.git-hooks") version "0.0.2"
}

gitHooks {
    setHooks(mapOf("pre-commit" to "checkInputs"))
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("test-junit5"))
    implementation("org.junit.jupiter:junit-jupiter-params")
}

sourceSets {
    test {
        kotlin.srcDir(rootDir.resolve("calendar"))
        resources.srcDir(rootDir.resolve("calendar"))
        resources.exclude("**/*.kt")
    }
}

tasks {
    test {
        useJUnitPlatform()
    }
    register("checkInputs") {
        doFirst {
            val violations = sourceSets.map(SourceSet::getResources).flatMap { ss ->
                ss.files.filter { it.readText().isNotBlank() }
            }
            violations.forEach {
                logger.error("Input file ${it.absolutePath} is not empty! Please clean it up before committing.")
            }
            if (violations.isNotEmpty()) error("Input file contents should not be committed. You can clean all of them by running ./gradlew cleanInputs")
        }
    }
    register("cleanInputs") {
        doFirst {
            val violations = sourceSets.map(SourceSet::getResources).flatMap { ss ->
                ss.files.filter { it.readText().isNotBlank() }
            }
            violations.forEach {
                it.writeText("")
                logger.warn("Cleaned input file ${it.absolutePath}")
            }
        }
    }
}