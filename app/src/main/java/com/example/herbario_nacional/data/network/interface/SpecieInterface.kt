package com.example.herbario_nacional.data.network.`interface`

import com.example.herbario_nacional.models.PlantSpecies
import retrofit2.http.GET

interface SpecieInterface {
    @GET("api/species/")
    suspend fun requestSpecie(): List<PlantSpecies>
}