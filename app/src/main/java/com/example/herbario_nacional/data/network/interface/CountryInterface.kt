package com.example.herbario_nacional.data.network.`interface`

import com.example.herbario_nacional.models.Request
import retrofit2.http.GET

interface CountryInterface {
    @GET("api/country/")
    suspend fun requestCountry(): Request
}