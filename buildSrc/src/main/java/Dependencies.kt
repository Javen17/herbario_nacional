object Versions {
    const val gradle = "3.5.1"
    const val kotlin = "1.3.50"

    const val koin = "2.0.1"
    const val koinArchitecture = "0.9.0-alpha-11"

    const val timber    = "4.7.1"
    const val constraintlayout = "1.1.3"
    const val appcompat = "1.0.2"
    const val lifecycle = "2.2.0-beta01"
    const val databinding = "3.1.4"

    const val retrofit = "2.6.1"
    const val gson = "2.8.5"
    const val interceptor = "3.8.1"

    const val rxJava = "2.1.9"
    const val rxAndroid = "2.0.2"
    const val googleMaterial = "1.0.0"

    const val androidTest = "1.1.0"
    const val vault = "1.4.2"
    const val junit = "4.12"
    const val testrunner = "1.1.1"
    const val espresso = "3.1.1"
    const val androidRules = "1.1.0"
    const val rootBeer = "0.0.7"

    const val coroutine = "1.3.1"
    const val coroutineAdapter = "0.9.2"

    const val coil = "0.7.0"
    const val coilTransformation = "0.0.3"

    const val room = "1.1.1"

    const val skeleton = "2.0.0"
}

object Kotlin {
    val build = "com.android.tools.build:gradle:${Versions.gradle}"
    val plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
}

object Libs {
    val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    val androidXcore ="androidx.core:core-ktx:${Versions.appcompat}"
    val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"
    val okHttp = "com.squareup.okhttp3:logging-interceptor:${Versions.interceptor}"
    val vault = "com.bottlerocketstudios:vault:${Versions.vault}"
    val rootBeer = "com.scottyab:rootbeer-lib:${Versions.rootBeer}"
    val lifecycleViewmodelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    val lifecycleViewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    val databinding = "com.android.databinding:compiler:${Versions.databinding}"
    val skeleton = "com.faltenreich:skeletonlayout:${Versions.skeleton}"
}

object Koin {
    val android = "org.koin:koin-android:${Versions.koin}"
    val architecture = "org.koin:koin-android-architecture:${Versions.koinArchitecture}"
    val viewModel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"
    val test = "org.koin:koin-test:${Versions.koin}"
}

object Retrofit {
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val retrofitConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
}

object Coroutines {
    val coroutine = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutine}"
    val coroutineAdapter = "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.coroutineAdapter}"
}

object GoogleLibs {
    val material = "com.google.android.material:material:${Versions.googleMaterial}"
    val gson = "com.google.code.gson:gson:${Versions.gson}"
}

object Coil {
    val coil = "io.coil-kt:coil:${Versions.coil}"
    val gif = "io.coil-kt:coil-gif:${Versions.coil}"
    val transformation = "com.github.Commit451.coil-transformations:transformations:${Versions.coilTransformation}"
    val transformationGpu = "com.github.Commit451.coil-transformations:transformations-gpu:${Versions.coilTransformation}"
}

object TestLibs {
    val junit = "junit:junit:${Versions.junit}"
    val androidTest = "androidx.test.ext:junit:${Versions.androidTest}"
    val testrunner = "androidx.test:runner:${Versions.testrunner}"
    val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    val androidRules = "androidx.test:rules:${Versions.androidRules}"
}