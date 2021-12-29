import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jlleitschuh.gradle.ktlint.KtlintExtension
// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    extra.apply {
        set("composeVersion", "1.0.5")
        set("roomVersion","2.3.0")
        set("koinVersion", "3.1.2")
        set("ktorVersion", "1.5.0")
    }
    repositories {
        google()
        mavenCentral()
        maven("https://plugins.gradle.org/m2/")
        maven(url = "https://dl.bintray.com/kotlin/kotlinx/")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.4")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31")
        classpath("org.jlleitschuh.gradle:ktlint-gradle:10.1.0")
        classpath(kotlin("gradle-plugin", version = "1.5.31"))
        classpath(kotlin("serialization", version = "1.5.31"))
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint") // Version should be inherited from parent

    configure<KtlintExtension> {
        disabledRules.set(
            setOf(
                "no-wildcard-imports", // IntelliJ generates wildcard imports sometimes and they're fine
                "filename" // We have a few classes that don't match the filenames but that's fine
            )
        )
    }

    tasks.withType(KotlinCompile::class).configureEach {
        // Opting in to using experimental APIs using the @OptIn annotation requires setting this flag
        kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
        kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlinx.coroutines.FlowPreview"
        kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.ExperimentalStdlibApi"
        // kotlinOptions.freeCompilerArgs += "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi"
    }
}
