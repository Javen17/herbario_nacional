package com.example.herbario_nacional.data.network.headerInterceptor

import com.example.herbario_nacional.preferences.AppPreferences
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    companion object ApiConstants{
        const val LOGIN = "/api/login/"
        const val PERMANENT_LOGIN = "/api/permanent_login/"
        const val GET_DEPARTMENTS = "/secured/obtainAllDepartments"
        const val GET_BUSINESS = "/secured/obtainAllBusiness"
        const val SEARCH_BUSINESS = "/secured/searchBusiness"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val cookies = AppPreferences().get(AppPreferences.Key.cookies, HashSet<String>()) as HashSet<*>
        println(cookies)
        when (request.url().url().path) {
            LOGIN -> {
                request = request
                    .newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .build()
            }
            PERMANENT_LOGIN -> {
                request = request
                    .newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .build()
            }
            GET_BUSINESS -> {
                request = request
                    .newBuilder()
                    .addHeader("Content-Type", "application/json")
                    //.addHeader("Cookie", AppPreferences().get(AppPreferences.Key.mega_token, "") as String)
                    .build()
            }
            SEARCH_BUSINESS -> {
                request = request
                    .newBuilder()
                    .addHeader("Content-Type", "application/json")
                    //.addHeader("Cookie", AppPreferences().get(AppPreferences.Key.mega_token, "") as String)
                    .build()
            }
            else -> {
                request = request
                    .newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .build()
            }
        }
        return chain.proceed(request)
    }
}
