package com.example.herbario_nacional.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.herbario_nacional.R
import com.example.herbario_nacional.base.BaseApplication
import com.example.herbario_nacional.data.network.Retry
import com.example.herbario_nacional.models.Register
import com.example.herbario_nacional.models.Status
import com.example.herbario_nacional.repo.RegisterRepository
import com.example.herbario_nacional.ui.Event
import com.example.herbario_nacional.util.StatusCode
import com.google.common.io.Resources
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import okhttp3.ResponseBody
import retrofit2.HttpException

class RegisterViewModel (private val registerRepository: RegisterRepository): ViewModel()  {
    private val _uiState = MutableLiveData<RegisterDataState>()
    val uiState: LiveData<RegisterDataState> get() = _uiState
    fun requestRegister(register: Register){
        viewModelScope.launch {
            Retry().retryIO(times = 3){
                runCatching {
                    emitUiState(showProgress = true)
                    registerRepository.registerUser(register)
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
        val dataState = RegisterDataState(showProgress, result, error)
        _uiState.value = dataState
    }

    data class RegisterDataState(val showProgress: Boolean, val result: Event<Status>?, val error: Event<String>?)
}