plugins {
    id("org.springframework.boot") version "2.5.4"
    id("io.freefair.lombok") version "6.1.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("java-library")
    id("java")
}

group = "com.simfund"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core
    implementation("com.fasterxml.jackson.core:jackson-core:2.12.5")
    // https://mvnrepository.com/artifact/com.google.guava/guava
    implementation("com.google.guava:guava:30.1.1-jre")
    implementation("com.graphql-java-kickstart:graphql-java-tools:11.0.1")
    implementation("com.graphql-java-kickstart:graphiql-spring-boot-starter:11.1.0")
    implementation("com.graphql-java-kickstart:graphql-spring-boot-starter:11.1.0")
    implementation("com.graphql-java-kickstart:graphql-webclient-spring-boot-starter:1.0.0")
    implementation("com.graphql-java-kickstart:voyager-spring-boot-starter:11.1.0")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("org.springframework.boot:spring-boot-devtools")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("com.graphql-java-kickstart:graphql-spring-boot-starter-test:11.1.0")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.compileJava {
    options.compilerArgs = listOf("-Xlint:unchecked")
}

tasks.test {
    useJUnitPlatform()
}
