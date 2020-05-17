package com.example.herbario_nacional.data.network.`interface`

import com.example.herbario_nacional.models.AndroidDevice
import com.example.herbario_nacional.models.AndroidDeviceUpdate
import com.example.herbario_nacional.models.Status
import retrofit2.http.*

interface AndroidDeviceInterface {
    @POST("api/register_android_device/")
    suspend fun requestPostAndroidDevice(@Body data: AndroidDevice): Status

    @PATCH("api/register_android_device/{id}/")
    suspend fun requestPutAndroidDevice(@Path(value = "id", encoded = true) value: Int, @Body data: AndroidDeviceUpdate): Status

   @GET("api/register_android_device/search/")
    suspend fun searchAndroidDeviceId(@Query("name") value: String): MutableList<AndroidDevice>
}