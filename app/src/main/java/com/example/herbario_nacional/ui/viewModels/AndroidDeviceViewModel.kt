package com.example.herbario_nacional.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.herbario_nacional.R
import com.example.herbario_nacional.base.BaseApplication
import com.example.herbario_nacional.data.network.Retry
import com.example.herbario_nacional.models.AndroidDevice
import com.example.herbario_nacional.models.Status
import com.example.herbario_nacional.repo.AndroidDeviceRepository
import kotlinx.coroutines.launch
import com.example.herbario_nacional.ui.Event
import com.example.herbario_nacional.util.StatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class AndroidDeviceViewModel (private val androidDeviceRepository: AndroidDeviceRepository): ViewModel() {
    private val _uiState = MutableLiveData<AndroidDeviceDataState>()

    val uiState: LiveData<AndroidDeviceDataState> get() = _uiState

    fun requestPostAndroiDevice(androidDevice: AndroidDevice){
        viewModelScope.launch {
            Retry().retryIO(times = 3){
                runCatching {
                    emitUiState(showProgress = true)
                    androidDeviceRepository.registerAndroidDevice(androidDevice)
                }.onSuccess {
                    emitUiState(result = Event(it))
                }.onFailure {
                    when(it){
                        is HttpException -> {
                            when(StatusCode(it.code()).description){
                                StatusCode.Status.BadRequest -> emitUiState(
                                    error = Event(withContext(Dispatchers.IO) {
                                        it.response()?.errorBody()!!.string()
                                    })
                                )
                                else -> BaseApplication.context.getString(R.string.internet_connection_error)
                            }
                        }
                        else -> BaseApplication.context.getString(R.string.internet_connection_error)
                    }
                }
            }
        }
    }

    private fun emitUiState(showProgress: Boolean = false, result: Event<Status>? = null, error: Event<String>? = null){
        val dataState = AndroidDeviceDataState(showProgress, result, error)
        _uiState.value = dataState
    }

    data class AndroidDeviceDataState(val showProgress: Boolean, val result: Event<Status>?, val error: Event<String>?)
}