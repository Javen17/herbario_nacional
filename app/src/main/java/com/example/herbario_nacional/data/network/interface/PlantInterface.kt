package com.example.herbario_nacional.data.network.`interface`

import com.example.herbario_nacional.models.PlantSpecimen
import com.example.herbario_nacional.models.Register
import com.example.herbario_nacional.models.Status
import retrofit2.http.Body
import retrofit2.http.POST

interface PlantInterface {
    @POST("api/plant_specimen/")
    suspend fun requestPlant(@Body data: PlantSpecimen): Status
}