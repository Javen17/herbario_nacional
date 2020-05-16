package com.example.herbario_nacional.ui.viewModels

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.herbario_nacional.R
import com.example.herbario_nacional.data.network.Retry
import com.example.herbario_nacional.models.Profile
import com.example.herbario_nacional.models.Status
import com.example.herbario_nacional.preferences.AppPreferences
import com.example.herbario_nacional.repo.ProfileRepository
import com.example.herbario_nacional.ui.Event
import com.example.herbario_nacional.util.StatusCode
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.HttpException

class ProfileViewModel (private val profileRepository: ProfileRepository): ViewModel()  {
    private val _uiState = MutableLiveData<ProfileDataState>()
    val uiState: LiveData<ProfileDataState>
        get() = _uiState

    fun requestProfile(profile: Profile){
        viewModelScope.launch {
            Retry().retryIO(times = 3){
                runCatching {
                    emitUiState(showProgress = true)
                    profileRepository.registerProfile(profile)
                }.onSuccess {
                    emitUiState(result = Event(it))
                }.onFailure {
                    when(it){
                        is HttpException -> {
                            when(StatusCode(it.code()).description){
                                StatusCode.Status.Unauthorized -> emitUiState(error = Event(R.string.unauthorized))
                                else -> emitUiState(error = Event(R.string.internet_connection_error))
                            }
                        }
                        else -> emitUiState(error = Event(R.string.internet_connection_error))
                    }
                }
            }
        }
    }


    fun updateProfile(photo: MultipartBody.Part, number_id: RequestBody, phone: RequestBody){
        viewModelScope.launch {
            Retry().retryIO(times = 3){
                runCatching {
                   // emitUiState(showProgress = true)
                    //Log.i("UPDATE PROFILE PROCESS", "ABOVE TO RUN REPOSITORY DATA:$data");
                    profileRepository.updateProfile(photo, number_id, phone)
                }.onSuccess {
                    /*TO COMPLETE*/
                    Log.i("UPDATE PROFILE PROCESS", "SEEMS TO BE SUCCESSFUL")
                }.onFailure {
                    when(it){
                        is HttpException -> {
                            Log.i("UPDATE PROFILE PROCESS", "SEEMS TO BE HAVE FAILED");
                            when(StatusCode(it.code()).description){
                                StatusCode.Status.Unauthorized -> emitUiState(error = Event(R.string.unauthorized))
                                else -> emitUiState(error = Event(R.string.internet_connection_error))
                            }
                        }

                        else -> {
                            Log.i("UPDATE PROFILE PROCESS", "SEEMS TO BE HAVE FAILED");
                            emitUiState(error = Event(R.string.internet_connection_error))
                        }
                    }
                }
            }
        }
    }

    fun updateProfileNoPhoto(number_id: RequestBody, phone: RequestBody){
        viewModelScope.launch {
            Retry().retryIO(times = 3){
                runCatching {
                    // emitUiState(showProgress = true)
                    //Log.i("UPDATE PROFILE PROCESS", "ABOVE TO RUN REPOSITORY DATA:$data");
                    profileRepository.updateProfileNoPhoto(number_id, phone)
                }.onSuccess {
                    /*TO COMPLETE*/
                    Log.i("UPDATE PROFILE PROCESS", "SEEMS TO BE SUCCESSFUL")
                }.onFailure {
                    when(it){
                        is HttpException -> {
                            Log.i("UPDATE PROFILE PROCESS", "SEEMS TO BE HAVE FAILED");
                            when(StatusCode(it.code()).description){
                                StatusCode.Status.Unauthorized -> emitUiState(error = Event(R.string.unauthorized))
                                else -> emitUiState(error = Event(R.string.internet_connection_error))
                            }
                        }

                        else -> {
                            Log.i("UPDATE PROFILE PROCESS", "SEEMS TO BE HAVE FAILED");
                            emitUiState(error = Event(R.string.internet_connection_error))
                        }
                    }
                }
            }
        }
    }

    private fun emitUiState(showProgress: Boolean = false, result: Event<Status>? = null, error: Event<Int>? = null){
        val dataState = ProfileDataState(showProgress, result, error)
        _uiState.value = dataState
    }

    fun createJsonObject(params: MutableMap<*, *>): RequestBody {
        return RequestBody.create(
            MediaType.parse("application/json; charset=utf-8"),
            JSONObject(params as Map<*, *>).toString()
        )
    }

    data class ProfileDataState(val showProgress: Boolean, val result: Event<Status>?, val error: Event<Int>?)
}