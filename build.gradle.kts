import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    jacoco
    `java-library`
    id("io.spring.dependency-management")
    id("org.springframework.boot")
}

group = "com.simfund"
version = "0.2.0-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(16))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    developmentOnly(Libs.spring_boot_devtools)

    implementation(Libs.de_fayard_buildsrclibs_gradle_plugin)
    implementation(Libs.jackson_core)
    implementation(Libs.guava)
    implementation(Libs.graphiql_spring_boot_starter)
    implementation(Libs.graphql_spring_boot_starter)
    implementation(Libs.mssql_jdbc)
    implementation(Libs.spring_boot_starter_data_jpa)
    implementation(Libs.spring_boot_starter_web)

    runtimeOnly(Libs.postgresql)
    runtimeOnly(Libs.spring_boot_devtools)

    testImplementation(Libs.reactor_test)
    testImplementation(Libs.spring_boot_starter_test)
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
            version = "0.2.0"
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