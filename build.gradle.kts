plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
}

allprojects {
    group = "dev.danielkindl.luvoq"
    version = "0.1.0"
}

tasks.register("formatCheck") {
    group = "verification"
    description = "Checks tracked text sources for trailing whitespace."

    doLast {
        val sourceFiles = rootProject.fileTree(rootDir) {
            include("**/*.kt")
            include("**/*.kts")
            include("**/*.xml")
            include("**/*.yml")
            include("**/*.yaml")
            exclude("**/build/**")
            exclude(".gradle/**")
            exclude(".kotlin/**")
        }
        val violations = sourceFiles.files.flatMap { file ->
            file.readLines().mapIndexedNotNull { index, line ->
                if (line.endsWith(" ") || line.endsWith("\t")) {
                    "${file.relativeTo(rootDir)}:${index + 1}"
                } else {
                    null
                }
            }
        }
        check(violations.isEmpty()) {
            "Trailing whitespace found in: ${violations.joinToString()}"
        }
    }
}
