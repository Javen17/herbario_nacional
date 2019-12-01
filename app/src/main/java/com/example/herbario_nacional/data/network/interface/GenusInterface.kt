package com.example.herbario_nacional.data.network.`interface`

import com.example.herbario_nacional.models.Genus
import retrofit2.http.GET

interface GenusInterface {
    @GET("api/genus/")
    suspend fun requestGenus(): List<Genus>
}