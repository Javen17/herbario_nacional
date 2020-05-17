package com.example.herbario_nacional.data.network.`interface`

import com.example.herbario_nacional.models.PlantSpecimen
import com.example.herbario_nacional.models.Status
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface PlantInterface {
    @GET("api/plant_specimen/")
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
    suspend fun searchPlantByName(@Query("species__common_name") value: String): MutableList<PlantSpecimen>

    @GET("api/plant_specimen/filter/")
    suspend fun searchPlantByFamily(@Query("species__genus__family__name") value: String): MutableList<PlantSpecimen>

    @GET("api/plant_specimen/filter/")
    suspend fun searchPlantByGenus(@Query("species__genus__name") value: String): MutableList<PlantSpecimen>

}