package com.example.herbario_nacional.data.network.`interface`

import com.example.herbario_nacional.models.Status
import com.example.herbario_nacional.models.funghi.FunghiSpecimen
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface FungusInterface {
    @GET("api/mushroom_specimen/approved/")
    suspend fun requestFungus(): MutableList<FunghiSpecimen>

    @Multipart
    @POST("api/mushroom_specimen/")
    suspend fun requestPostFungus(
        @Part photo: MultipartBody.Part,
        @Part("user") user: RequestBody,
        @Part("date_received") date_received: RequestBody,
        @Part("number_of_samples") number_of_samples: RequestBody,
        @Part("description") description: RequestBody,
        @Part("cap") cap: RequestBody,
        @Part("forms") forms: RequestBody,
        @Part("crust") crust: RequestBody,
        @Part("color") color: RequestBody,
        @Part("change_of_color") change_of_color: RequestBody,
        @Part("species") species: RequestBody,
        @Part("smell") smell: RequestBody,
        @Part("status") status: RequestBody,
        @Part("city") city: RequestBody,
        @Part("ecosystem") ecosystem: RequestBody,
        @Part("recolection_area_status") recolection_area_status: RequestBody,
        @Part("latitude") latitude: RequestBody,
        @Part("longitude") longitude: RequestBody,
        @Part("aditional_info") aditional_info: RequestBody,
        @Part("location") location: RequestBody
    ): Status

    @GET("api/mushroom_specimen/filter/")
    suspend fun searchFungusByName(@Query("species__common_name") value: String): MutableList<FunghiSpecimen>

    @GET("api/mushroom_specimen/filter/")
    suspend fun searchFungusByRecollectionArea(@Query("recolection_area_status__name") value: String): MutableList<FunghiSpecimen>

    @GET("api/mushroom_specimen/filter/")
    suspend fun searchFungusByLocation(@Query("location") value: String): MutableList<FunghiSpecimen>
}