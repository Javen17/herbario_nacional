package com.example.herbario_nacional.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.herbario_nacional.R
import com.example.herbario_nacional.models.RecolectionAreaStatus
import com.example.herbario_nacional.repo.HabitatDescriptionRepository
import com.example.herbario_nacional.ui.Event
import kotlinx.coroutines.launch

class HabitatDescriptionViewModel (private val habitatDescriptionRepository: HabitatDescriptionRepository): ViewModel() {

    private val _uiState = MutableLiveData<HabitatDescriptionDataState>()

    val uiState: LiveData<HabitatDescriptionDataState>
        get() = _uiState

    init {
        requestHabitatDescription()
    }

    fun requestHabitatDescription() {
        viewModelScope.launch {
            runCatching {
                emitUiState(showProgress = true)
                habitatDescriptionRepository.getHabitatDescription()
            }.onSuccess {
                emitUiState(result = Event(it))
            }.onFailure {
                emitUiState(error = Event(R.string.internet_connection_error))
            }
        }
    }

    fun emitUiState(
        showProgress: Boolean = false,
        result: Event<List<RecolectionAreaStatus>>? = null,
        error: Event<Int>? = null){
        val dataState = HabitatDescriptionDataState(
            showProgress,
            result,
            error
        )
        _uiState.value = dataState
    }

    data class HabitatDescriptionDataState(
        val showProgress: Boolean,
        val result: Event<List<RecolectionAreaStatus>>?,
        val error: Event<Int>?)
}