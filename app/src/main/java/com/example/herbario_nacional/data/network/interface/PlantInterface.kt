package com.example.herbario_nacional.data.network.`interface`

import com.example.herbario_nacional.models.PlantSpecimen
import com.example.herbario_nacional.models.Status
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface PlantInterface {
    @GET("api/plant_specimen/approved/")
    suspend fun requestPlant(): MutableList<PlantSpecimen>

    @Multipart
    @POST("api/plant_specimen/")
    suspend fun requestPostPlant(
        @Part photo: MultipartBody.Part,
        @Part("user") user: RequestBody,
        @Part("date_received") date_received: RequestBody,
        @Part("biostatus") biostatus: RequestBody,
        @Part("species") species: RequestBody,
        @Part("complete") complete: RequestBody,
        @Part("status") status: RequestBody,
        @Part("number_of_samples") number_of_samples: RequestBody,
        @Part("description") description: RequestBody,
        @Part("ecosystem") ecosystem: RequestBody,
        @Part("recolection_area_status") recolection_area_status: RequestBody,
        @Part("city") city: RequestBody,
        @Part("latitude") latitude: RequestBody,
        @Part("longitude") longitude: RequestBody,
        @Part("location") location: RequestBody
    ): Status

    @GET("api/plant_specimen/filter/")
    suspend fun searchPlantByName(@Query("species__common_name") value: String): MutableList<Any?>

    @GET("api/plant_specimen/filter/")
    suspend fun searchPlantByLocation(@Query("location") value: String): MutableList<Any?>

    @GET("api/plant_specimen/filter/")
    suspend fun searchPlantByRecollectionArea(@Query("recolection_area_status__name") value: String): MutableList<Any?>

}