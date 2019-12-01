package com.example.herbario_nacional.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.herbario_nacional.R
import com.example.herbario_nacional.models.PlantSpecimen
import com.example.herbario_nacional.repo.PlantRepository
import com.example.herbario_nacional.ui.Event
import kotlinx.coroutines.launch

class PlantViewModel (private val plantRepository: PlantRepository): ViewModel() {

    private val _uiState = MutableLiveData<PlantDataState>()

    val uiState: LiveData<PlantDataState>
        get() = _uiState

    init {
        requestPlant()
    }

    fun requestPlant() {
        viewModelScope.launch {
            runCatching {
                emitUiState(showProgress = true)
                plantRepository.getPlants()
            }.onSuccess {
                emitUiState(result = Event(it))
            }.onFailure {
                emitUiState(error = Event(R.string.internet_connection_error))
            }
        }
    }

    fun emitUiState(
        showProgress: Boolean = false,
        result: Event<MutableList<PlantSpecimen>>? = null,
        error: Event<Int>? = null){
        val dataState = PlantDataState(
            showProgress,
            result,
            error
        )
        _uiState.value = dataState
    }

    data class PlantDataState(
        val showProgress: Boolean,
        val result: Event<MutableList<PlantSpecimen>>?,
        val error: Event<Int>?)
}