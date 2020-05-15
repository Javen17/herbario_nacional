package com.example.herbario_nacional.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.herbario_nacional.R
import com.example.herbario_nacional.models.Ecosystem
import com.example.herbario_nacional.repo.HabitatRepository
import com.example.herbario_nacional.ui.Event
import kotlinx.coroutines.launch

class HabitatViewModel (private val habitatRepository: HabitatRepository): ViewModel() {

    private val _uiState = MutableLiveData<HabitatDataState>()

    val uiState: LiveData<HabitatDataState>
        get() = _uiState

    init {
        requestHabitat()
    }

    fun requestHabitat() {
        viewModelScope.launch {
            runCatching {
                emitUiState(showProgress = true)
                habitatRepository.getHabitats()
            }.onSuccess {
                emitUiState(result = Event(it))
            }.onFailure {
                emitUiState(error = Event(R.string.internet_connection_error))
            }
        }
    }

    fun emitUiState(
        showProgress: Boolean = false,
        result: Event<List<Ecosystem>>? = null,
        error: Event<Int>? = null){
        val dataState = HabitatDataState(
            showProgress,
            result,
            error
        )
        _uiState.value = dataState
    }

    data class HabitatDataState(
        val showProgress: Boolean,
        val result: Event<List<Ecosystem>>?,
        val error: Event<Int>?)
}