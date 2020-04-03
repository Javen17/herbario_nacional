package com.example.herbario_nacional.data.network.headerInterceptor

import com.example.herbario_nacional.preferences.AppPreferences
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    companion object ApiConstants{
        const val LOGIN = "/api/login/"
        const val SIGN_UP = "/api/sign_up/"
        const val PROFILE = "api/profile/"
        const val COUNTRY = "api/country/"
        const val STATE = "api/state/"
        const val CITY = "api/city/"
        const val FAMILY = "api/family/"
        const val GENUS = "api/genus/"
        const val SPECIES = "api/species/"
        const val STATUS = "api/status/"
        const val ECOSYSTEM = "api/ecosystem/"
        const val RECOLECTION_AREA_STATUS = "api/recolection_area_status/"
        const val BIOSTATUS = "api/biostatus/"
        const val PLANT_SPECIMEN = "api/plant_specimen"
        const val FUNGHI_SPECIMEN = "api/mushroom_specimen"
        const val FORM_TYPE = "api/mushroom_form_type/"
        const val CAP_TYPE = "api/mushroom_cap_type/"
        const val PERMANENT_LOGIN = "/api/permanent_login/"
        const val ME = "api/me/"
    }

    private fun setupCookies(){
        val cookies = AppPreferences().get(AppPreferences.Key.cookies, HashSet<String>()) as HashSet<*>
        var content = ""
        cookies.forEach {
            content += it as String
        }
        requestBuilder.addHeader("Cookie", content)
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
            PROFILE -> {
                requestBuilder
                    .addHeader("Content-Type", "application/json")
            }
            PLANT_SPECIMEN -> {
                requestBuilder
                    .addHeader("Content-Type","multipart/form-data")
            }
            FUNGHI_SPECIMEN -> {
                requestBuilder
                    .addHeader("Content-Type", "application/json")
            }
            FORM_TYPE -> {
                requestBuilder
                    .addHeader("Content-Type", "application/json")
            }
            CAP_TYPE -> {
                requestBuilder
                    .addHeader("Content-Type", "application/json")
            }
            COUNTRY -> {
                requestBuilder
                    .addHeader("Content-Type", "application/json")
            }
            STATE -> {
                requestBuilder
                    .addHeader("Content-Type", "application/json")
            }
            CITY -> {
                requestBuilder
                    .addHeader("Content-Type", "application/json")
            }
            FAMILY -> {
                requestBuilder
                    .addHeader("Content-Type", "application/json")
            }
            GENUS -> {
                requestBuilder
                    .addHeader("Content-Type", "application/json")
            }
            SPECIES -> {
                requestBuilder
                    .addHeader("Content-Type", "application/json")
            }
            STATUS -> {
                requestBuilder
                    .addHeader("Content-Type", "application/json")
            }
            ECOSYSTEM -> {
                requestBuilder
                    .addHeader("Content-Type", "application/json")
            }
            RECOLECTION_AREA_STATUS -> {
                requestBuilder
                    .addHeader("Content-Type", "application/json")
            }
            BIOSTATUS -> {
                requestBuilder
                    .addHeader("Content-Type", "application/json")
            }
            PERMANENT_LOGIN -> {
                requestBuilder
                    .addHeader("Content-Type", "application/json")
            }
            ME -> {
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