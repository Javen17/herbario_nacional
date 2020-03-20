package com.example.herbario_nacional.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.herbario_nacional.R
import com.example.herbario_nacional.data.network.Retry
import com.example.herbario_nacional.models.Register
import com.example.herbario_nacional.models.Status
import com.example.herbario_nacional.repo.RegisterRepository
import com.example.herbario_nacional.ui.Event
import com.example.herbario_nacional.util.StatusCode
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RegisterViewModel (private val registerRepository: RegisterRepository): ViewModel()  {
    private val _uiState = MutableLiveData<RegisterDataState>()
    val uiState: LiveData<RegisterDataState> get() = _uiState
    fun requestRegister(first_name: String, last_name: String, username: String, email: String, password: String, is_staff: Boolean, is_active: Boolean, is_superuser: Boolean, date_joined: String?, name: String, last_login: String?){
        viewModelScope.launch {
            Retry().retryIO(times = 3){
                runCatching {
                    emitUiState(showProgress = true)
                    registerRepository.registerUser(Register(first_name, last_name, username, email, password, is_staff, is_active, is_superuser, date_joined, name, last_login))
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

    private fun emitUiState(showProgress: Boolean = false, result: Event<Status>? = null, error: Event<Int>? = null){
        val dataState = RegisterDataState(showProgress, result, error)
        _uiState.value = dataState
    }

    data class RegisterDataState(val showProgress: Boolean, val result: Event<Status>?, val error: Event<Int>?)
}