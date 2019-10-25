object Versions {
    const val kotlin = "1.3.50"
    const val ktx = "1.1.0"
    const val constraintLayoutV = "1.1.3"
    const val junit = "4.12"
    const val runner = "1.1.1"
    const val espresso = "3.2.0"
    const val recyclerviewV = "28.0.0"
    const val cardviewV = "1.0.0"
    const val glideV = "4.10.0"
}

object Kotlin{
    val plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    val jdk = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
}

object Android {
    val recyclerview = "com.android.support:recyclerview-v7:${Versions.recyclerviewV}"
}

object Androidx{
    val appCompat = "androidx.appcompat:appcompat:${Versions.ktx}"
    val core = "androidx.core:core-ktx:${Versions.ktx}"
    val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayoutV}"
    val cardview = "androidx.cardview:cardview:${Versions.cardviewV}"
}

object TestLibs {
    val junit = "junit:junit:${Versions.junit}"
    val testRunner = "androidx.test:runner:${Versions.runner}"
    val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
}

object Github {
    val glide = "com.github.bumptech.glide:glide:${Versions.glideV}"
    val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glideV}"
}