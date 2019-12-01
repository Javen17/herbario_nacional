package com.example.herbario_nacional.data.network.`interface`

import com.example.herbario_nacional.models.PlantStatus
import retrofit2.http.GET

interface StatusInterface {
    @GET("api/status/")
    suspend fun requestStatus(): List<PlantStatus>
}