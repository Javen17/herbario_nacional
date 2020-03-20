package com.example.herbario_nacional.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.herbario_nacional.R
import com.example.herbario_nacional.data.network.Retry
import com.example.herbario_nacional.models.Credentials
import com.example.herbario_nacional.models.Status
import com.example.herbario_nacional.repo.CredentialsRepository
import com.example.herbario_nacional.ui.Event
import com.example.herbario_nacional.util.StatusCode
import kotlinx.coroutines.launch
import retrofit2.HttpException

class CredentialsViewModel (private val credentialsRepository: CredentialsRepository): ViewModel() {

    private val _uiState = MutableLiveData<CredentialsDataState>()
    val uiState: LiveData<CredentialsDataState> get() = _uiState

    fun requestLogin(username: String, password: String){
        viewModelScope.launch {
            Retry().retryIO(times = 3){
                runCatching {
                    emitUiState(showProgress = true)
                    credentialsRepository.getLogin(Credentials(password,username))
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
        val dataState = CredentialsDataState(showProgress, result, error)
        _uiState.value = dataState
    }

    data class CredentialsDataState(val showProgress: Boolean, val result: Event<Status>?, val error: Event<Int>?)
}
