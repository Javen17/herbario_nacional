package com.example.herbario_nacional.data.network.`interface`

import com.example.herbario_nacional.models.Ecosystem
import retrofit2.http.GET

interface HabitatInterface {
    @GET("api/ecosystem/")
    suspend fun requestEcosystem(): List<Ecosystem>
}