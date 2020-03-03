package com.example.herbario_nacional.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.herbario_nacional.R
import com.example.herbario_nacional.models.funghi.FunghiSpecimen
import com.example.herbario_nacional.repo.FungusRepository
import com.example.herbario_nacional.ui.Event
import kotlinx.coroutines.launch

class FungusViewModel(private val fungusRepository: FungusRepository): ViewModel() {
    private val _uiState = MutableLiveData<FunghiDataState>()

    val uiState: LiveData<FunghiDataState>
        get() = _uiState

    init {
        requestFunghi()
    }

    fun requestFunghi() {
        viewModelScope.launch {
            runCatching {
                emitUiState(showProgress = true)
                fungusRepository.getFungus()
            }.onSuccess {
                emitUiState(result = Event(it))
            }.onFailure {
                emitUiState(error = Event(R.string.internet_connection_error))
            }
        }
    }

    fun emitUiState(
        showProgress: Boolean = false,
        result: Event<MutableList<FunghiSpecimen>>? = null,
        error: Event<Int>? = null){
        val dataState = FunghiDataState(
            showProgress,
            result,
            error
        )
        _uiState.value = dataState
    }

    data class FunghiDataState(
        val showProgress: Boolean,
        val result: Event<MutableList<FunghiSpecimen>>?,
        val error: Event<Int>?)
}