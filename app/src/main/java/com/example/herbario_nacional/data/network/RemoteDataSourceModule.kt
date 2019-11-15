package com.example.herbario_nacional.data.network

import com.example.herbario_nacional.BuildConfig
import com.example.herbario_nacional.data.network.`interface`.CredentialsInterface
import com.example.herbario_nacional.data.network.adapters.CoroutineCallAdapterFactory
import com.example.herbario_nacional.data.network.cookiesInterceptor.ReceivedCookieInterceptor
import com.example.herbario_nacional.data.network.headerInterceptor.HeaderInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val remoteDataSourceModule = module {
    single { (refreshToken: String, permanentToken: String, accessToken: String) -> createOkHttpClient(refreshToken, permanentToken, accessToken) }
    single { createWebService<CredentialsInterface>(get(), BuildConfig.HERBARIO_URL) }
}

fun createOkHttpClient(refreshToken: String, permanentToken: String, accessToken: String): OkHttpClient {
    return getOkHttpClient(refreshToken, permanentToken, accessToken)
}

fun getOkHttpClient(refreshToken: String, permanentToken: String, accessToken: String): OkHttpClient{
    return OkHttpClient
        .Builder()
        .addInterceptor(HeaderInterceptor(refreshToken, permanentToken, accessToken))
        .apply {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            interceptors().add(ReceivedCookieInterceptor())
        }
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(okHttpClient)
        .build()
    return retrofit.create(T::class.java)
}