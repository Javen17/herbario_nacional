package com.example.herbario_nacional.repo

import android.util.Log
import com.example.herbario_nacional.data.network.`interface`.ProfileInterface
import com.example.herbario_nacional.models.Profile
import com.example.herbario_nacional.models.Status
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject

interface ProfileRepository {
    suspend fun registerProfile(data: Profile): Status
    suspend fun updateProfile(photo: MultipartBody.Part, number_id: RequestBody, phone: RequestBody): Status
    suspend fun updateProfileNoPhoto(number_id: RequestBody, phone: RequestBody): Status

}

class ProfileRepositoryImpl(private val profileService: ProfileInterface): ProfileRepository {
    override suspend fun registerProfile(data: Profile): Status {
        return profileService.requestProfile(data)
    }

    override suspend fun updateProfile(photo: MultipartBody.Part, number_id: RequestBody, phone: RequestBody): Status {
        //Log.i("UPDATE PROFILE", "REACHED PROFILE REPOSITORY UPDATE FUNCTION: data:$data");
        //image, number_id, phone
        return profileService.updateProfile(photo= photo, number_id = number_id, phone = phone);
    }

    override suspend fun updateProfileNoPhoto(number_id: RequestBody, phone: RequestBody): Status {
        //Log.i("UPDATE PROFILE", "REACHED PROFILE REPOSITORY UPDATE FUNCTION: data:$data");
        //image, number_id, phone
        return profileService.updateProfileNoPhoto(number_id = number_id, phone = phone);
    }
}
