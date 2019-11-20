package com.example.herbario_nacional.data.network.cookiesInterceptor

import com.example.herbario_nacional.preferences.AppPreferences
import okhttp3.Interceptor
import okhttp3.Response

class CookieInterceptor: Interceptor {
    override fun intercept(chain:Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.addHeader("token-access", AppPreferences().get(AppPreferences.Key.token_access, "") as String)
        builder.addHeader("token-permanent", AppPreferences().get(AppPreferences.Key.token_permanent, "") as String)
        builder.addHeader("token-refresh", AppPreferences().get(AppPreferences.Key.token_refresh, "") as String)
        return chain.proceed(builder.build())
    }
}