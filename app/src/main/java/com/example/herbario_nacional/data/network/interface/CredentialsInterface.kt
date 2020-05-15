package com.example.herbario_nacional.data.network.`interface`

import com.example.herbario_nacional.models.Credentials
import com.example.herbario_nacional.models.EmailData
import com.example.herbario_nacional.models.Message
import com.example.herbario_nacional.models.Status
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CredentialsInterface {
    @GET("api/permanent_login/")
    suspend fun requestPermanetLogin(): Message

    @POST("api/login/")
    suspend fun requestLogin(@Body credentials: Credentials): Status


    @POST("api/me/restore_password/")
    suspend fun resetPassword(@Body email: EmailData): Status

}