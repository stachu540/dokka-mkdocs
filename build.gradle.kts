import com.jfrog.bintray.gradle.BintrayExtension
import org.gradle.jvm.tasks.Jar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    `maven-publish`
    kotlin("jvm") version "1.3.40"
    id("com.jfrog.bintray") version "1.8.4"
    id("com.github.johnrengelman.shadow") version "5.1.0"
    id("org.jetbrains.dokka") version "0.9.18"
}

repositories {
    jcenter()
}

dependencies {
    compile("org.jetbrains.dokka:dokka-fatjar:0.9.18")
}

val sourcesJar by tasks.creating(Jar::class) {
    dependsOn(tasks.classes)
    classifier = "sources"
    from(sourceSets.main.get().allSource)
}

val javadocJar by tasks.creating(Jar::class) {
    dependsOn(tasks.dokka)
    classifier = "javadoc"
    from(tasks.dokka.get().outputDirectory)
}

tasks {
    dokka {
        outputFormat = "javadoc"
    }

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    withType<Wrapper> {
        gradleVersion = "5.4.1"
        distributionType = Wrapper.DistributionType.ALL
    }

    jar {
        enabled = false
    }

    shadowJar {
        baseName = rootProject.name
        classifier = null

        manifest {
            attributes += "Main-Class" to "org.jetbrains.dokka.MainKt"
        }

        configurations {
            exclude(compileOnly.get().asFileTree.map { it.absolutePath })
        }

        exclude(
            "colorScheme/**",
            "fileTemplates/**",
            "inspectionDescriptions/**",
            "intentionDescriptions/**",
            "src/**"
        )

        relocate("kotlin.reflect.full", "kotlin.reflect")
    }

    publish {
        dependsOn += bintrayUpload
    }
}

artifacts {
    archives(sourcesJar)
    archives(javadocJar)
}

publishing {
    repositories {
        maven {
            name = "github"
            url = uri("https://maven.pkg.github.com/stachu540")
            mavenContent {
                releasesOnly()
            }
            credentials {
                username = "stachu540"
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        create<MavenPublication>("dokkaFatJar") {
            project.shadow.component(this)
            artifact(sourcesJar)
            artifact(javadocJar)
            pom
        }
    }
}

bintray {
    user = "stachu540"
    key = System.getenv("BINTRAY_KEY")
    setPublications("dokkaFatJar")
    pkg(closureOf<BintrayExtension.PackageConfig> {
        repo = "Java"
        name = "dokka-mkdocs"
        userOrg = "stachu540"
        desc = "Dokka extension to generate MkDocs sites"
        vcsUrl = "https://github.com/stachu540/dokka-mkdocs.git"
        setLicenses("MIT")
    })
}