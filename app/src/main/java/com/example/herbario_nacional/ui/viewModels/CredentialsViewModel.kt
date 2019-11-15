package com.example.herbario_nacional.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.herbario_nacional.R
import com.example.herbario_nacional.data.network.Retry
import com.example.herbario_nacional.models.Credentials
import com.example.herbario_nacional.preferences.AppPreferences
import com.example.herbario_nacional.repo.CredentialsRepository
import com.example.herbario_nacional.ui.Event
import kotlinx.coroutines.launch

class CredentialsViewModel (private val credentialsRepository: CredentialsRepository): ViewModel() {

    private val _uiState = MutableLiveData<CredentialsDataState>()
    val uiState: LiveData<CredentialsDataState> get() = _uiState

    init {
        viewModelScope.launch {
            Retry().retryIO(times = 3){
                runCatching {
                    emitUiState(showProgress = true)
                    credentialsRepository.getLogin(Credentials("admin","admin"))
                }.onSuccess {
                    emitUiState(sessionToken = Event(it))
                }.onFailure {
                    emitUiState(error = Event(R.string.internet_connection_error))
                }
            }
        }
    }

    private fun emitUiState(showProgress: Boolean = false, sessionToken: Event<Void>? = null, error: Event<Int>? = null){
        val dataState = CredentialsDataState(showProgress, sessionToken, error)
        _uiState.value = dataState
    }

    data class CredentialsDataState(val showProgress: Boolean, val sessionToken: Event<Void>?, val error: Event<Int>?)

}
