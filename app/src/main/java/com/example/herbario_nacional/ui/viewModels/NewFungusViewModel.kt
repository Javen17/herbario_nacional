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
import retrofit2.HttpException

class NewFungusViewModel (private val fungusRepository: FungusRepository): ViewModel()  {
    private val _uiState = MutableLiveData<FungusDataState>()

    val uiState: LiveData<FungusDataState> get() = _uiState

    fun requestPostFungus(fungus: PostFungusSpecimen){
        viewModelScope.launch {
            Retry().retryIO(times = 3){
                runCatching {
                    emitUiState(showProgress = true)
                    fungusRepository.postFungus(fungus)
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