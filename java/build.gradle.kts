import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.testing.Test
import org.gradle.jvm.toolchain.JavaLanguageVersion

subprojects {
    pluginManager.apply("java-library")

    extensions.configure<JavaPluginExtension> {
        toolchain.languageVersion.set(JavaLanguageVersion.of(21))
    }

    repositories {
        mavenCentral()
    }

    dependencies {
        add("testImplementation", platform("org.junit:junit-bom:5.10.2"))
        add("testImplementation", "org.junit.jupiter:junit-jupiter")
        add("testImplementation", "org.assertj:assertj-core:3.26.0")
    }

    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
    }
}
