package com.example.herbario_nacional.data.network.`interface`

import com.example.herbario_nacional.models.location.City
import retrofit2.http.GET

interface CityInterface {
    @GET("api/city/")
    suspend fun requestCity(): List<City>
}