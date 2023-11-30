plugins {
    java
    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.4"
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
}

group = "com.mrexception"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("com.google.guava:guava:28.1-jre")
    implementation("org.apache.commons:commons-text:1.8")
    annotationProcessor("org.projectlombok:lombok")
    implementation("org.projectlombok:lombok")

    testAnnotationProcessor("org.projectlombok:lombok")
    testImplementation("org.projectlombok:lombok")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
    maxHeapSize = "2g"
}
