package com.example.herbario_nacional.data.network.`interface`

import com.example.herbario_nacional.models.MeData
import com.example.herbario_nacional.models.Profile
import com.example.herbario_nacional.models.Status
import retrofit2.http.Body
import retrofit2.http.PUT

interface AccountInterface {

    @PUT("api/me/modify_account/")
    suspend fun updateAccount(@Body data: MeData): Status
}