package com.example.herbario_nacional.data.network.headerInterceptor

import com.example.herbario_nacional.preferences.AppPreferences
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    companion object ApiConstants{
        const val LOGIN = "/api/login/"
        const val SIGN_UP = "/api/sign_up/"
        const val PERMANENT_LOGIN = "/api/permanent_login/"
        const val GET_DEPARTMENTS = "/secured/obtainAllDepartments"
        const val GET_BUSINESS = "/secured/obtainAllBusiness"
        const val SEARCH_BUSINESS = "/secured/searchBusiness"
    }


    private fun setupCookies(){
        val cookies = AppPreferences().get(AppPreferences.Key.cookies, HashSet<String>()) as HashSet<*>
        cookies.forEach {
            requestBuilder.addHeader("Cookie", it as String)
        }
    }

    private lateinit var requestBuilder: Request.Builder

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        requestBuilder = request.newBuilder()
        when (request.url().url().path) {
            LOGIN -> {
                requestBuilder
                    .addHeader("Content-Type", "application/json")
            }
            SIGN_UP -> {
                requestBuilder
                    .addHeader("Content-Type", "application/json")
            }
            PERMANENT_LOGIN -> {
                requestBuilder
                    .addHeader("Content-Type", "application/json")
            }
            GET_BUSINESS -> {
                requestBuilder
                    .addHeader("Content-Type", "application/json")
            }
            SEARCH_BUSINESS -> {
                requestBuilder
                    .addHeader("Content-Type", "application/json")
            }
            else -> {
                requestBuilder
                    .addHeader("Content-Type", "application/json")
            }
        }
        setupCookies()
        request = requestBuilder.build()
        return chain.proceed(request)
    }
}
