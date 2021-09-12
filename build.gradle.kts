import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    jacoco
    java
    `java-library`
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.springframework.boot") version "2.5.4"
}

group = "com.simfund"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(16))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    implementation("com.fasterxml.jackson.core:jackson-core:2.12.5")
    implementation("com.google.guava:guava:30.1.1-jre")
    implementation("com.graphql-java-kickstart:graphiql-spring-boot-starter:11.1.0")
    implementation("com.graphql-java-kickstart:graphql-spring-boot-starter:11.1.0")
    implementation("com.microsoft.sqlserver:mssql-jdbc:9.4.0.jre16")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")

    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.compileJava {
    options.compilerArgs = listOf("-Xlint:deprecation", "-Xlint:unchecked")
}

tasks.test {
    useJUnitPlatform()
}

//-------- building the super, awesome, uber jar --------
springBoot {
    buildInfo {
        properties {
            artifact = "pdf-platform"
            group = "com.simfund.graphql"
            name = "Pdf Platform"
            version = "0.0.1"
        }
    }
    mainClass.set("com.simfund.graphql.pdfplatform.Application")
}

val copyNecessaryFiles by tasks.creating(Copy::class) {
    from("$projectDir/src/main/resources") {
        include("application.yml", "graphql/*.graphqls")
    }
    into("$rootDir/docker/app/build")
}

tasks.getByName<Jar>("jar") {
    enabled = false
}

tasks.getByName<BootJar>("bootJar") {
    archiveBaseName.set("pdf-platform")
    destinationDirectory.set(file("$rootDir/docker/app/build"))
    launchScript()
}