package com.example.herbario_nacional.data.network.`interface`

import com.example.herbario_nacional.models.Me
import com.example.herbario_nacional.models.MeData
import com.example.herbario_nacional.models.Status
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.PUT

interface MeInterface {
    @GET("api/me/")
    suspend fun requestMe(): Me


    @PUT("api/me/modify_account/")
    suspend fun updateAccount(@Body data: RequestBody): Status
}