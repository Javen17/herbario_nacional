package com.example.herbario_nacional.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.herbario_nacional.R
import com.example.herbario_nacional.data.network.Retry
import com.example.herbario_nacional.models.funghi.PostFungusSpecimen
import com.example.herbario_nacional.models.Status
import com.example.herbario_nacional.repo.FungusRepository
import com.example.herbario_nacional.ui.Event
import com.example.herbario_nacional.util.StatusCode
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException

class NewFungusViewModel (private val fungusRepository: FungusRepository): ViewModel()  {
    private val _uiState = MutableLiveData<FungusDataState>()

    val uiState: LiveData<FungusDataState> get() = _uiState

    fun requestPostFungus(
        photo: MultipartBody.Part,
        user: RequestBody,
        date_received: RequestBody,
        number_of_samples: RequestBody,
        description: RequestBody,
        cap: RequestBody,
        forms: RequestBody,
        crust: RequestBody,
        color: RequestBody,
        change_of_color: RequestBody,
        species: RequestBody,
        smell: RequestBody,
        status: RequestBody,
        city: RequestBody,
        ecosystem: RequestBody,
        recolection_area_status: RequestBody,
        latitude: RequestBody,
        longitude: RequestBody,
        aditional_info: RequestBody,
        location: RequestBody
    ){
        viewModelScope.launch {
            Retry().retryIO(times = 3){
                runCatching {
                    emitUiState(showProgress = true)
                    fungusRepository.postFungus(
                        photo,
                        user,
                        date_received,
                        number_of_samples,
                        description,
                        cap,
                        forms,
                        crust,
                        color,
                        change_of_color,
                        species,
                        smell,
                        status,
                        city,
                        ecosystem,
                        recolection_area_status,
                        latitude,
                        longitude,
                        aditional_info,
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
        val dataState = FungusDataState(showProgress, status, error)
        _uiState.value = dataState
    }

    data class FungusDataState(val showProgress: Boolean, val status: Event<Status>?, val error: Event<Int>?)
}