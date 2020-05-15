package com.example.herbario_nacional.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.herbario_nacional.R
import com.example.herbario_nacional.models.Biostatus
import com.example.herbario_nacional.repo.BiostatusRepository
import com.example.herbario_nacional.ui.Event
import kotlinx.coroutines.launch

class BiostatusViewModel (private val biostatusRepository: BiostatusRepository): ViewModel() {

    private val _uiState = MutableLiveData<BiostatusDataState>()

    val uiState: LiveData<BiostatusDataState>
        get() = _uiState

    init {
        requestBiostatus()
    }

    fun requestBiostatus() {
        viewModelScope.launch {
            runCatching {
                emitUiState(showProgress = true)
                biostatusRepository.getBiostatus()
            }.onSuccess {
                emitUiState(result = Event(it))
            }.onFailure {
                emitUiState(error = Event(R.string.internet_connection_error))
            }
        }
    }

    fun emitUiState(
        showProgress: Boolean = false,
        result: Event<List<Biostatus>>? = null,
        error: Event<Int>? = null){
        val dataState = BiostatusDataState(
            showProgress,
            result,
            error
        )
        _uiState.value = dataState
    }

    data class BiostatusDataState(
        val showProgress: Boolean,
        val result: Event<List<Biostatus>>?,
        val error: Event<Int>?)
}