package com.example.herbario_nacional.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.herbario_nacional.R
import com.example.herbario_nacional.models.PlantStatus
import com.example.herbario_nacional.repo.StatusRepository
import com.example.herbario_nacional.ui.Event
import kotlinx.coroutines.launch

class StatusViewModel (private val statusRepository: StatusRepository): ViewModel() {

    private val _uiState = MutableLiveData<StatusDataState>()

    val uiState: LiveData<StatusDataState>
        get() = _uiState

    init {
        requestStatus()
    }

    fun requestStatus() {
        viewModelScope.launch {
            runCatching {
                emitUiState(showProgress = true)
                statusRepository.getStatus()
            }.onSuccess {
                emitUiState(result = Event(it))
            }.onFailure {
                emitUiState(error = Event(R.string.internet_connection_error))
            }
        }
    }

    fun emitUiState(
        showProgress: Boolean = false,
        result: Event<List<PlantStatus>>? = null,
        error: Event<Int>? = null){
        val dataState = StatusDataState(
            showProgress,
            result,
            error
        )
        _uiState.value = dataState
    }

    data class StatusDataState(
        val showProgress: Boolean,
        val result: Event<List<PlantStatus>>?,
        val error: Event<Int>?)
}