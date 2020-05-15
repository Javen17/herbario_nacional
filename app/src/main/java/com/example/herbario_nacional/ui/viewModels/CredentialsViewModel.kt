package com.example.herbario_nacional.ui.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.herbario_nacional.R
import com.example.herbario_nacional.base.BaseApplication
import com.example.herbario_nacional.data.network.Retry
import com.example.herbario_nacional.models.Credentials
import com.example.herbario_nacional.models.EmailData
import com.example.herbario_nacional.models.Status
import com.example.herbario_nacional.repo.CredentialsRepository
import com.example.herbario_nacional.ui.Event
import com.example.herbario_nacional.util.StatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
                            StatusCode.Status.Unauthorized -> emitUiState(
                                error = Event( withContext(Dispatchers.IO) {
                                    it.response()?.errorBody()!!.string()
                                }))
                            else -> emitUiState(error = Event(BaseApplication.context.getString(R.string.internet_connection_error)))
                            }
                        }
                        else -> emitUiState(error = Event(BaseApplication.context.getString(R.string.internet_connection_error)))
                    }
                }
            }
        }
    }

    fun resetPassword(email: EmailData){
        viewModelScope.launch {
            Retry().retryIO(times = 3){
                runCatching {
                    //emitUiState(showProgress = true)
                    Log.i("RESET PASSWORD", "VIEW MODEL");
                    credentialsRepository.resetPassword(email)
                }.onSuccess {
                    //emitUiState(result = Event(it))
                    Log.i("RESET PASSWORD", "SUCCESS");
                }.onFailure {
                    Log.i("RESET PASSWORD", "FAILED");
                }
            }
        }
    }

    private fun emitUiState(showProgress: Boolean = false, result: Event<Status>? = null, error: Event<String>? = null){
        val dataState = CredentialsDataState(showProgress, result, error)
        _uiState.value = dataState
    }

    data class CredentialsDataState(val showProgress: Boolean, val result: Event<Status>?, val error: Event<String>?)
}
