package com.example.herbario_nacional.data.network.`interface`

import com.example.herbario_nacional.models.RecolectionAreaStatus
import retrofit2.http.GET

interface HabitatDescriptionInterface {
    @GET("api/recolection_area_status/")
    suspend fun requestRecolectionAreaStatus(): List<RecolectionAreaStatus>
}