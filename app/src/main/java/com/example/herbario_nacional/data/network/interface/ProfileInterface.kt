package com.example.herbario_nacional.data.network.`interface`

import com.example.herbario_nacional.models.Profile
import com.example.herbario_nacional.models.Status
import retrofit2.http.Body
import retrofit2.http.POST

interface ProfileInterface {
    @POST("api/profile/")
    suspend fun requestProfile(@Body data: Profile): Status
}