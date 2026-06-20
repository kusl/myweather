import com.android.build.api.dsl.ApkSigningConfig
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
}

/**
 * Resolve release-signing material from (in priority order):
 *   1. Environment variables  (used by CI — see .github/workflows/build_apk.yml)
 *   2. keystore.properties     (used for local release builds; git-ignored)
 *
 * If neither is present we return null and the release variant falls back to the
 * debug signing config, so the project still assembles for forks/contributors
 * who don't have the secrets. Nothing here ever commits a secret.
 */
fun resolveReleaseSigning(): Map<String, String>? {
    val env = System.getenv()
    if (env["MYWEATHER_KEYSTORE_PATH"]?.isNotBlank() == true) {
        return mapOf(
            "storeFile" to env.getValue("MYWEATHER_KEYSTORE_PATH"),
            "storePassword" to (env["MYWEATHER_KEYSTORE_PASSWORD"] ?: ""),
            "keyAlias" to (env["MYWEATHER_KEY_ALIAS"] ?: ""),
            "keyPassword" to (env["MYWEATHER_KEY_PASSWORD"] ?: ""),
        )
    }
    val propsFile = rootProject.file("keystore.properties")
    if (propsFile.exists()) {
        val props = Properties().apply { propsFile.inputStream().use { load(it) } }
        val storeFile = props.getProperty("storeFile").orEmpty()
        if (storeFile.isNotBlank()) {
            return mapOf(
                "storeFile" to storeFile,
                "storePassword" to props.getProperty("storePassword", ""),
                "keyAlias" to props.getProperty("keyAlias", ""),
                "keyPassword" to props.getProperty("keyPassword", ""),
            )
        }
    }
    return null
}

android {
    // Code package for the generated R and BuildConfig classes. This is internal
    // and never user-visible, so it stays as-is: every `import com.kusl.myweather.*`
    // (including R and BuildConfig) and the ProGuard keep-rules depend on it.
    // It is intentionally NOT the same as applicationId below — AGP allows them to
    // differ, and renaming it would mean rewriting every package declaration.
    namespace = "com.kusl.myweather"

    // Compile against API 37 — required by androidx.lifecycle 2.11.0, whose AAR
    // metadata mandates compileSdk >= 37 — while still targeting the requested
    // API 35 and supporting Android 14 (API 34) as the floor.
    compileSdk = 37

    defaultConfig {
        // Installable / store-facing application identity. Deliberately distinct
        // from `namespace` above: the app is published under this GitHub-org-style
        // id. Debug builds append ".debug" (see below) so a debug and a release
        // build can be installed side by side on the same device.
        applicationId = "com.github.kusl.myweather"
        minSdk = 34
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables { useSupportLibrary = true }
    }

    val releaseSigning = resolveReleaseSigning()
    signingConfigs {
        if (releaseSigning != null) {
            create("release") {
                storeFile = file(releaseSigning.getValue("storeFile"))
                storePassword = releaseSigning["storePassword"]
                keyAlias = releaseSigning["keyAlias"]
                keyPassword = releaseSigning["keyPassword"]
                enableV1Signing = true
                enableV2Signing = true
            }
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            // Sign with the real release key when available; otherwise fall back
            // to the debug key so `assembleRelease` never fails for lack of secrets.
            signingConfig = signingConfigs.findByName("release")
                ?: signingConfigs.getByName("debug")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    testOptions {
        unitTests {
            // Let JVM unit tests touch android.jar stubs (e.g. android.util.Log)
            // without throwing "not mocked" — they no-op and return defaults.
            isReturnDefaultValues = true
        }
    }

    // Room schema export — handy for migration diffing and committed under app/schemas.
    ksp { arg("room.schemaLocation", "$projectDir/schemas") }
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

dependencies {
    // Core + lifecycle
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.activity.compose)

    // Compose (BOM-managed)
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Settings storage
    implementation(libs.androidx.datastore.preferences)

    // Room (KSP is the only annotation processor in the project)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // Networking: Retrofit + OkHttp + kotlinx.serialization
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.kotlinx.serialization)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.kotlinx.serialization.json)

    // Unit tests (JVM — run in CI without an emulator)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)

    // Instrumented tests (device/emulator)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.arch.core.testing)
    androidTestImplementation(libs.androidx.room.testing)
    androidTestImplementation(libs.androidx.ui.test.junit4)
}
