package com.example.herbario_nacional.data.network.`interface`

import com.example.herbario_nacional.models.PlantSpecies
import com.example.herbario_nacional.models.PlantSpecimen
import com.example.herbario_nacional.models.PostPlantSpecimen
import com.example.herbario_nacional.models.Status
import com.example.herbario_nacional.util.StatusCode
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PlantInterface {

    @GET("api/plant_specimen/")
    suspend fun requestPlant(): MutableList<PlantSpecimen>

    @POST("api/plant_specimen/")
    suspend fun requestPostPlant(@Body data: PostPlantSpecimen): Status

    @GET("api/plant_specimen/search/?species__common_name={value}")
    suspend fun searchPlantByName(value: String): MutableList<PlantSpecimen>
}
