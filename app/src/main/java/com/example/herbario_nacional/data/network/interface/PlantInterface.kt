package com.example.herbario_nacional.data.network.`interface`

import com.example.herbario_nacional.models.PlantSpecimen
import com.example.herbario_nacional.models.PostPlantSpecimen
import com.example.herbario_nacional.models.Status
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PlantInterface {
    @GET("api/plant_specimen/")
    suspend fun requestPlant(): MutableList<PlantSpecimen>

    @POST("api/plant_specimen/")
    suspend fun requestPostPlant(@Body data: PostPlantSpecimen): Status
}