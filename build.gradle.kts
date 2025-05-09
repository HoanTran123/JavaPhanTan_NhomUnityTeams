plugins {
    id 'java'

}

group 'iuh.fit'
version '1.0-SNAPSHOT'

repositories {
    mavenCentral()
}
compileJava { options.release = 17 }

ext {
    junitVersion = '5.11.0'
}

sourceCompatibility = '17'
targetCompatibility = '17'

tasks.withType(JavaCompile) {
    options.encoding = 'UTF-8'
}

dependencies {
    compileOnly('jakarta.servlet:jakarta.servlet-api:6.1.0')
    implementation('org.hibernate:hibernate-core:7.0.0.Beta1')
    implementation('org.glassfish.jaxb:jaxb-runtime:4.0.5')
// https://mvnrepository.com/artifact/org.mariadb.jdbc/mariadb-java-client
    implementation 'org.mariadb.jdbc:mariadb-java-client:3.5.1'
// https://mvnrepository.com/artifact/com.microsoft.sqlserver/mssql-jdbc
    implementation 'com.microsoft.sqlserver:mssql-jdbc:11.2.3.jre17'
// https://mvnrepository.com/artifact/org.projectlombok/lombok
    compileOnly 'org.projectlombok:lombok:1.18.36'
    annotationProcessor 'org.projectlombok:lombok:1.18.36'
// https://mvnrepository.com/artifact/net.datafaker/datafaker
    implementation 'net.datafaker:datafaker:2.4.2'
    implementation 'org.apache.poi:poi:5.2.3'
    implementation 'org.apache.poi:poi-ooxml:5.2.3'
    testImplementation("org.junit.jupiter:junit-jupiter-api:${junitVersion}")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${junitVersion}")
}

test {
    useJUnitPlatform()
}