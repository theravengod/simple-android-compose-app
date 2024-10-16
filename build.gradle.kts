import org.jetbrains.kotlin.gradle.dsl.KotlinCompile
import org.jlleitschuh.gradle.ktlint.KtlintExtension

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.jetbrainsKotlinSerializatiobn) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.googleKsp) apply false
    alias(libs.plugins.ktlintGradle) apply false
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
