plugins {
    id 'java'
}

group 'iuh.fit'
version '1.0-SNAPSHOT'

repositories {
    mavenCentral()
}

ext {
    junitVersion = '5.11.0'
}

sourceCompatibility = '22'
targetCompatibility = '22'

tasks.withType(JavaCompile) {
    options.encoding = 'UTF-8'
}

dependencies {
    implementation('org.hibernate:hibernate-core:7.0.0.Beta1')
    implementation('org.glassfish.jaxb:jaxb-runtime:4.0.5')
    implementation 'org.mariadb.jdbc:mariadb-java-client:3.5.1'
    compileOnly 'org.projectlombok:lombok:1.18.36'
    annotationProcessor 'org.projectlombok:lombok:1.18.36'
    implementation 'net.datafaker:datafaker:2.4.2'
    implementation 'com.microsoft.sqlserver:mssql-jdbc:12.3.0.jre17-preview'
    testImplementation("org.junit.jupiter:junit-jupiter-api:${junitVersion}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junitVersion}")
}

test {
    useJUnitPlatform()}