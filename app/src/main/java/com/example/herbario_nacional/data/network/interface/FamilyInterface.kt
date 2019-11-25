package com.example.herbario_nacional.data.network.`interface`

import com.example.herbario_nacional.models.PlantFamily
import retrofit2.http.GET

interface FamilyInterface {
    @GET("api/family/")
    suspend fun requestFamily(): List<PlantFamily>
}