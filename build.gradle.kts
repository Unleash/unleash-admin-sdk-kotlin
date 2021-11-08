import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.net.URL

plugins {
    kotlin("jvm").version("1.5.31")
    id("org.jetbrains.dokka").version("1.5.0")
    `java-library`
    `maven-publish`
    signing
    jacoco
    id("com.github.nbaztec.coveralls-jacoco").version("1.2.12")
    id("io.github.gradle-nexus.publish-plugin").version("1.1.0")
    id("pl.allegro.tech.build.axion-release").version("1.13.2")
    id("com.diffplug.spotless").version("5.17.1")
}

val tagVersion = System.getenv("GITHUB_REF")?.split('/')?.last()

project.version = scmVersion.version

repositories {
    mavenCentral()
}

jacoco {
    toolVersion = "0.8.7"
}

val assertJVersion = "3.21.0"
val gsonVersion = "2.8.9"
val junitVersion = "5.8.1"
val okHttpVersion = "4.9.2"
val slf4jVersion = "1.7.32"

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation(kotlin("stdlib-jdk8"))
    api("com.squareup.okhttp3:okhttp:$okHttpVersion")
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("com.google.code.gson:gson:$gsonVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    testImplementation("org.assertj:assertj-core:$assertJVersion")
    testImplementation("com.squareup.okhttp3:mockwebserver:$okHttpVersion")
    testImplementation("org.slf4j:slf4j-simple:$slf4jVersion")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        showExceptions = true
        showStackTraces = true
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        events("passed", "skipped", "failed")
    }
    finalizedBy(tasks.jacocoTestReport)
}


tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report
    reports {
        xml.required.set(true)
        html.required.set(true)
    }

}

tasks.jar {
    manifest {
        attributes(mapOf("Implementation-Title" to project.name, "Implementation-Version" to project.version))
    }
}

tasks.withType<DokkaTask>().configureEach {
    dokkaSourceSets {
        named("main") {
            moduleName.set("Unleash Admin Client")
            sourceLink {
                localDirectory.set(file("src/main/kotlin"))
                remoteUrl.set(URL("https://github.com/Unleash/unleash-admin-sdk-kotlin/tree/${tagVersion ?: "main"}/src/main/kotlin"))
                remoteLineSuffix.set("#L")
            }
        }
    }
}

java {
    withSourcesJar()
    withJavadocJar()
}

val sonatypeUsername: String? by project
val sonatypePassword: String? by project
val group: String? by project

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            groupId = group
            artifactId = "unleash-admin-sdk"
            version = "${version}"
            pom {
                name.set("Unleash Admin SDK")
                description.set("Admin SDK for Unleash")
                url.set("https://docs.getunleash.io/unleash-admin-sdk-kotlin/index.html")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("chrkolst")
                        name.set("Christopher Kolstad")
                        email.set("chriswk@getunleash.ai")
                    }
                    developer {
                        id.set("ivarconr")
                        name.set("Ivar Conradi Østhus")
                        email.set("ivarconr@getunleash.ai")
                    }
                }
                scm {
                    connection.set("scm:git:https://github.com/Unleash/unleash-admin-sdk-kotlin")
                    developerConnection.set("scm:git:ssh://git@github.com:Unleash/unleash-admin-sdk-kotlin")
                    url.set("https://github.com/Unleash/unleash-admin-sdk-kotlin")
                }
            }
        }
    }
    repositories {
        maven {
            url = uri(layout.buildDirectory.dir("repo"))
            name = "test"
        }
    }
}

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
            username.set(sonatypeUsername)
            password.set(sonatypePassword)
        }
    }
}

val signingKey: String? by project
val signingPassphrase: String? by project
signing {
    if (signingKey != null && signingPassphrase != null) {
        useInMemoryPgpKeys(signingKey, signingPassphrase)
        sign(publishing.publications["mavenJava"])
    }
}
