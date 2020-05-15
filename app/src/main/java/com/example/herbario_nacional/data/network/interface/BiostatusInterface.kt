package com.example.herbario_nacional.data.network.`interface`

import com.example.herbario_nacional.models.Biostatus
import retrofit2.http.GET

interface BiostatusInterface {
    @GET("api/biostatus/")
    suspend fun requestBiostatus(): List<Biostatus>
}