package com.example.herbario_nacional.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.herbario_nacional.R
import com.example.herbario_nacional.models.funghi.CapType
import com.example.herbario_nacional.repo.CapTypeRepository
import com.example.herbario_nacional.ui.Event
import kotlinx.coroutines.launch

class CapTypeViewModel (private val capTypeRepository: CapTypeRepository): ViewModel() {

    private val _uiState = MutableLiveData<CapTypeDataState>()

    val uiState: LiveData<CapTypeDataState>
        get() = _uiState

    init {
        requestCapType()
    }

    fun requestCapType() {
        viewModelScope.launch {
            runCatching {
                emitUiState(showProgress = true)
                capTypeRepository.getFormType()
            }.onSuccess {
                emitUiState(result = Event(it))
            }.onFailure {
                emitUiState(error = Event(R.string.internet_connection_error))
            }
        }
    }

    fun emitUiState(
        showProgress: Boolean = false,
        result: Event<List<CapType>>? = null,
        error: Event<Int>? = null){
        val dataState = CapTypeDataState(
            showProgress,
            result,
            error
        )
        _uiState.value = dataState
    }

    data class CapTypeDataState(
        val showProgress: Boolean,
        val result: Event<List<CapType>>?,
        val error: Event<Int>?)
}