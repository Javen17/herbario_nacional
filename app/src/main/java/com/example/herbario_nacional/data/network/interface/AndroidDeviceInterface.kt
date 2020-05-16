package com.example.herbario_nacional.data.network.`interface`

import com.example.herbario_nacional.models.AndroidDevice
import com.example.herbario_nacional.models.Status
import retrofit2.http.Body
import retrofit2.http.POST

interface AndroidDeviceInterface {
    @POST("api/register_android_device/")
    suspend fun requestPostAndroidDevice(@Body data: AndroidDevice): Status
}