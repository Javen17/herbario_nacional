package com.example.herbario_nacional.data.network.`interface`

import com.example.herbario_nacional.models.Register
import com.example.herbario_nacional.models.Status
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterInterface {
    @POST("api/register/")
    suspend fun requestRegister(@Body data: Register): Status
}