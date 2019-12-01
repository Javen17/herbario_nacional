package com.example.herbario_nacional.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.herbario_nacional.R
import com.example.herbario_nacional.models.location.State
import com.example.herbario_nacional.repo.StateRepository
import com.example.herbario_nacional.ui.Event
import kotlinx.coroutines.launch

class StateViewModel (private val stateRepository: StateRepository): ViewModel() {

    private val _uiState = MutableLiveData<StateDataState>()

    val uiState: LiveData<StateDataState>
        get() = _uiState

    init {
        requestState()
    }

    fun requestState() {
        viewModelScope.launch {
            runCatching {
                emitUiState(showProgress = true)
                stateRepository.getStates()
            }.onSuccess {
                emitUiState(result = Event(it))
            }.onFailure {
                emitUiState(error = Event(R.string.internet_connection_error))
            }
        }
    }

    fun emitUiState(
        showProgress: Boolean = false,
        result: Event<List<State>>? = null,
        error: Event<Int>? = null){
        val dataState = StateDataState(
            showProgress,
            result,
            error
        )
        _uiState.value = dataState
    }

    data class StateDataState(
        val showProgress: Boolean,
        val result: Event<List<State>>?,
        val error: Event<Int>?)
}