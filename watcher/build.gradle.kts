@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    id("maven-publish")
}

android {
    namespace = "io.github.santimattius.android.deeplink.watcher"
    compileSdk = extraString("target_sdk_version").toInt()

    defaultConfig {
        minSdk = extraString("min_sdk_version").toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}


composeCompiler {
    enableStrongSkippingMode = true
    reportsDestination = layout.buildDirectory.dir("compose_compiler")
}

dependencies {

    implementation(kotlin("reflect"))
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)

    //Lifecycle
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.lifecycle.runtime.compose)

    //Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.activity.compose)
    implementation(libs.compose.ui)
    implementation(libs.ui.graphics)
    implementation(libs.compose.tooling.preview)
    implementation(libs.material3)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.compose.ui.test.junit)

    //Coroutine
    implementation(libs.bundles.coroutine)

    //Startup
    implementation(libs.startup.android)

    //Room
    implementation(libs.room)
    implementation(libs.room.ktx)
    debugImplementation(libs.compose.tooling)
    debugImplementation(libs.compose.ui.test.manifest)
    ksp(libs.room.compiler)

    testImplementation(libs.junit)

    androidTestImplementation(libs.test.ext)
    androidTestImplementation(libs.test.espresso)
}

fun extraString(key: String): String {
    return extra[key] as String
}

afterEvaluate {
    publishing {
        publications {
            // Creates a Maven publication called "release".
            register<MavenPublication>("release") {
                from(components["release"])
                groupId = extraString("group_id")
                artifactId = extraString("artifact_id")
                version = extraString("lib_version")
            }
        }
    }
}