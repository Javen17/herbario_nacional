package com.example.herbario_nacional.data.network.`interface`

import com.example.herbario_nacional.models.Profile
import com.example.herbario_nacional.models.Status
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.http.*

interface ProfileInterface {
    @POST("api/profile/")
    suspend fun requestProfile(@Body data: Profile): Status

    @Multipart
    @PUT("api/me/modify_profile/")
    suspend fun updateProfile(

        @Part photo: MultipartBody.Part,
        @Part("number_id") number_id: RequestBody,
        @Part("phone") phone: RequestBody): Status

    @Multipart
    @PUT("api/me/modify_profile/")
    suspend fun updateProfileNoPhoto(
        @Part("number_id") number_id: RequestBody,
        @Part("phone") phone: RequestBody): Status

}
