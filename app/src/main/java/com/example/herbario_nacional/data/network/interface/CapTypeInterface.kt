package com.example.herbario_nacional.data.network.`interface`

import com.example.herbario_nacional.models.funghi.CapType
import retrofit2.http.GET

interface CapTypeInterface {
    @GET("api/mushroom_cap_type")
    suspend fun requestCapType(): List<CapType>
}