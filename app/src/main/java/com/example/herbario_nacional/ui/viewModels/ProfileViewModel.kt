package com.example.herbario_nacional.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.herbario_nacional.R
import com.example.herbario_nacional.data.network.Retry
import com.example.herbario_nacional.models.Profile
import com.example.herbario_nacional.models.Register
import com.example.herbario_nacional.models.Status
import com.example.herbario_nacional.repo.ProfileRepository
import com.example.herbario_nacional.repo.RegisterRepository
import com.example.herbario_nacional.ui.Event
import com.example.herbario_nacional.util.StatusCode
import kotlinx.coroutines.launch
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

    private fun emitUiState(showProgress: Boolean = false, result: Event<Status>? = null, error: Event<Int>? = null){
        val dataState = ProfileDataState(showProgress, result, error)
        _uiState.value = dataState
    }

    data class ProfileDataState(val showProgress: Boolean, val result: Event<Status>?, val error: Event<Int>?)
}