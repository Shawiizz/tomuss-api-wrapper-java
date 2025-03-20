plugins {
    java
    kotlin("jvm")
    id("com.gradleup.shadow") version "9.0.0-beta4"
    `maven-publish`
}

group = "fr.shawiizz"
version = "1.0.18"

repositories {
    mavenCentral()
}

publishing {
    publications {
        create<MavenPublication>("library") {
            from(components["java"])
        }
    }
    repositories {
        maven {
            url = uri("https://forge.univ-lyon1.fr/api/v4/projects/38352/packages/maven")
            credentials(HttpHeaderCredentials::class) {
                name = "Private-Token"
                value =
                    findProperty("gitLabPrivateToken") as String? // the variable resides in $GRADLE_USER_HOME/gradle.properties
            }
            authentication {
                create("header", HttpHeaderAuthentication::class)
            }
        }
    }
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.squareup.okhttp3:okhttp:+")
    implementation("com.squareup.okhttp3:okhttp-urlconnection:+")
    implementation("com.squareup.okhttp3:okhttp-java-net-cookiejar:+")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    implementation("com.fasterxml.jackson.core:jackson-core:2.15.2")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.15.2")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}
