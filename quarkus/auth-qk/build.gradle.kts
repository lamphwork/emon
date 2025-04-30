plugins {
    java
    id("io.quarkus") version "3.21.4"
    id("org.jooq.jooq-codegen-gradle") version "3.20.3"
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation(project(":core:auth"))
    implementation(project(":quarkus:common-qk"))

    implementation("org.jooq:jooq:3.20.3")
    jooqCodegen("org.jooq:jooq-meta-extensions:3.20.3")

    compileOnly("org.projectlombok:lombok:1.18.38")
    annotationProcessor("org.projectlombok:lombok:1.18.38")
    testCompileOnly("org.projectlombok:lombok:1.18.38")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.38")

    implementation(enforcedPlatform("io.quarkus.platform:quarkus-bom:3.21.4"))
    implementation("io.quarkus:quarkus-rest")
    implementation("io.quarkus:quarkus-rest-jackson")
    implementation("io.quarkus:quarkus-flyway")
    implementation("io.quarkus:quarkus-smallrye-jwt")
    implementation("io.quarkus:quarkus-jdbc-postgresql")
    implementation("io.quarkus:quarkus-arc")
    testImplementation("io.quarkus:quarkus-junit5")
}

group = "lamph"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

jooq {
    configuration {
        generator {
            target {
                packageName = "lamph.quarkus.auth"
            }
            database {
                name = "org.jooq.meta.extensions.ddl.DDLDatabase"
                properties {
                    property {
                        key = "scripts"
                        value = "src/main/resources/migration"
                    }
                    property {
                        key = "sort"
                        value = "semantic"
                    }
                    property {
                        key = "unqualifiedSchema"
                        value = "none"
                    }
                    property {
                        key = "defaultNameCase"
                        value = "lower"
                    }
                }
            }
        }
    }
}

tasks.named("compileJava") {
    dependsOn("jooqCodegen")
}

sourceSets {
    named("main") {
        java {
            srcDir("build/generated/sources/jooq") // Đường dẫn sinh code
        }
    }
}


tasks.withType<Test> {
    systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
}
tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}
