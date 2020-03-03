package com.example.herbario_nacional.data.network.`interface`

import com.example.herbario_nacional.models.funghi.PostFungusSpecimen
import com.example.herbario_nacional.models.Status
import com.example.herbario_nacional.models.funghi.FunghiSpecimen
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface FungusInterface {
    @GET("api/mushroom_specimen/")
    suspend fun requestFungus(): MutableList<FunghiSpecimen>

    @POST("api/mushroom_specimen/")
    suspend fun requestPostFungus(@Body data: PostFungusSpecimen): Status
}