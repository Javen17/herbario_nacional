package com.example.herbario_nacional.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.herbario_nacional.R
import com.example.herbario_nacional.data.network.Retry
import com.example.herbario_nacional.models.PlantSpecimen
import com.example.herbario_nacional.models.PostPlantSpecimen
import com.example.herbario_nacional.models.Register
import com.example.herbario_nacional.models.Status
import com.example.herbario_nacional.repo.PlantRepository
import com.example.herbario_nacional.repo.RegisterRepository
import com.example.herbario_nacional.ui.Event
import com.example.herbario_nacional.util.StatusCode
import com.google.gson.JsonObject
import kotlinx.coroutines.launch
import retrofit2.HttpException

class NewPlantViewModel (private val plantRepository: PlantRepository): ViewModel()  {
    private val _uiState = MutableLiveData<PlantDataState>()

    val uiState: LiveData<PlantDataState> get() = _uiState

    fun requestPostPlant(plant: PostPlantSpecimen){
        viewModelScope.launch {
            Retry().retryIO(times = 3){
                runCatching {
                    emitUiState(showProgress = true)
                    plantRepository.postPlant(plant)
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