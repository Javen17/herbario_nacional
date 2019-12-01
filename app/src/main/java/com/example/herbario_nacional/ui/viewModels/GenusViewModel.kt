package com.example.herbario_nacional.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.herbario_nacional.R
import com.example.herbario_nacional.models.Genus
import com.example.herbario_nacional.repo.GenusRepository
import com.example.herbario_nacional.ui.Event
import kotlinx.coroutines.launch

class GenusViewModel (private val genusRepository: GenusRepository): ViewModel() {

    private val _uiState = MutableLiveData<GenusDataState>()

    val uiState: LiveData<GenusDataState>
        get() = _uiState

    init {
        requestGenus()
    }

    fun requestGenus() {
        viewModelScope.launch {
            runCatching {
                emitUiState(showProgress = true)
                genusRepository.getGenuses()
            }.onSuccess {
                emitUiState(result = Event(it))
            }.onFailure {
                emitUiState(error = Event(R.string.internet_connection_error))
            }
        }
    }

    fun emitUiState(
        showProgress: Boolean = false,
        result: Event<List<Genus>>? = null,
        error: Event<Int>? = null){
        val dataState = GenusDataState(
            showProgress,
            result,
            error
        )
        _uiState.value = dataState
    }

    data class GenusDataState(
        val showProgress: Boolean,
        val result: Event<List<Genus>>?,
        val error: Event<Int>?)
}