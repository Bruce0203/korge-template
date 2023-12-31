import korlibs.korge.gradle.KorgeGradlePlugin
import korlibs.korge.gradle.Orientation
import korlibs.korge.gradle.korge
import korlibs.korge.gradle.kormaVersion
import korlibs.korge.gradle.targets.ProjectType
import korlibs.korge.gradle.targets.desktop.configureNativeDesktop
import korlibs.korge.gradle.targets.desktop.configureNativeDesktopCross
import korlibs.korge.gradle.targets.desktop.configureNativeDesktopRun

apply<KorgeGradlePlugin>()
apply(plugin = "org.jetbrains.kotlin.plugin.serialization")
plugins {
    kotlin("multiplatform")
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.korge)
}

korge {
    id = "io.github.bruce0203.${rootProject.name}"
    targetAll()
    targetJvm()
    targetJs()
    targetDesktop()
    //targetDesktopCross()
    targetIos()
    targetAndroid()
    entryPoint = "runMain"
    orientation = Orientation.LANDSCAPE
    icon = File(projectDir, "src/commonMain/resources/images/logo.png"
        .replace("/", File.separator))
//    exeBaseName = ""
//    name = ""
    androidManifestChunks.addAll(setOf(
        """<uses-permission android:name="android.permission.INTERNET" />""",
        """<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />"""
    ))
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

kotlin {
    sourceSets {
        val commonMain by getting {
//            kotlin.addSrcDir(File(project(":shared").projectDir, "src/commonMain/kotlin"))
            dependencies {
                api("de.cketti.unicode:kotlin-codepoints-deluxe:0.6.1")
                api(project(":deps"))
                api(project(":shared"))
            }
        }
    }
}


fun SourceDirectorySet.addSrcDir(file: File) {
    setSrcDirs(srcDirs.apply { add(file) })
}

@Suppress("UnstableApiUsage")
tasks.withType<ProcessResources> {
    filesMatching("client.properties") {
        expand(rootProject.properties)
    }
}
