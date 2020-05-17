package com.example.herbario_nacional.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.herbario_nacional.R
import com.example.herbario_nacional.data.network.Retry
import com.example.herbario_nacional.models.Status
import com.example.herbario_nacional.repo.PlantRepository
import com.example.herbario_nacional.ui.Event
import com.example.herbario_nacional.util.StatusCode
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException

class NewPlantViewModel (private val plantRepository: PlantRepository): ViewModel()  {
    private val _uiState = MutableLiveData<PlantDataState>()

    val uiState: LiveData<PlantDataState> get() = _uiState

    fun requestPostPlant(
        photo: MultipartBody.Part,
        user: RequestBody,
        date_received: RequestBody,
        biostatus: RequestBody,
        species: RequestBody,
        complete: RequestBody,
        status: RequestBody,
        number_of_samples: RequestBody,
        description: RequestBody,
        ecosystem: RequestBody,
        recolection_area_status: RequestBody,
        city: RequestBody,
        latitude: RequestBody,
        longitude: RequestBody,
        location: RequestBody
    ){
        viewModelScope.launch {
            Retry().retryIO(times = 3){
                runCatching {
                    emitUiState(showProgress = true)
                    plantRepository.postPlant(
                        photo,
                        user,
                        date_received,
                        biostatus,
                        species,
                        complete,
                        status,
                        number_of_samples,
                        description,
                        ecosystem,
                        recolection_area_status,
                        city,
                        latitude,
                        longitude,
                        location
                    )
                }.onSuccess {
                    emitUiState(status = Event(it))
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

    private fun emitUiState(showProgress: Boolean = false, status: Event<Status>? = null, error: Event<Int>? = null){
        val dataState = PlantDataState(showProgress, status, error)
        _uiState.value = dataState
    }

    data class PlantDataState(val showProgress: Boolean, val status: Event<Status>?, val error: Event<Int>?)
}