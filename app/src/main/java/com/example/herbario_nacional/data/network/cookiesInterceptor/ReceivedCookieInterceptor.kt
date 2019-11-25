package com.example.herbario_nacional.data.network.cookiesInterceptor

import com.example.herbario_nacional.preferences.AppPreferences
import okhttp3.Interceptor
import okhttp3.Response

class ReceivedCookieInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        if (originalResponse.headers("Set-Cookie").isNotEmpty())
        {
            val cookies: HashSet<String> = hashSetOf()
            for (header in originalResponse.headers("Set-Cookie")) {
                val Newheader = "$header; domain:https://django-acacia.herokuapp.com;"
                println("OBTUVE COOKIE: $Newheader")
                cookies.add(Newheader)
            }
            AppPreferences().put(AppPreferences.Key.cookies, cookies)
        }
        return originalResponse
    }
}