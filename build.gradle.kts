import org.jetbrains.kotlin.gradle.dsl.KotlinCompile
import org.jlleitschuh.gradle.ktlint.KtlintExtension

project.extra["composeCompilerVersion"] = "1.5.8" // Get it from here: https://developer.android.com/jetpack/androidx/releases/compose-kotlin
project.extra["composeBOMVersion"] = "2023.10.01" // Get it from here : https://developer.android.com/jetpack/compose/bom/bom-mapping

plugins {
    val agpVersion = "8.2.2"
    val kotlinVersion = "1.9.22" // Make sure it's compatible with composeCompilerVersion

    id("org.jetbrains.kotlin.android") version kotlinVersion apply false
    id("com.android.application") version agpVersion apply false
    id("com.android.library") version agpVersion apply false
    id("com.google.devtools.ksp") version "1.9.22-1.0.17" apply false  // Get it from here: https://github.com/google/ksp/releases - first part needs to match the Kotlin compiler version
    id("org.jlleitschuh.gradle.ktlint") version "12.1.0"
    kotlin("plugin.serialization") version "1.9.22"
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
