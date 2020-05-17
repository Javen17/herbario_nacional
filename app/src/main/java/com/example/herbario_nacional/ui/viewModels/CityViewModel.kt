package com.example.herbario_nacional.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.herbario_nacional.R
import com.example.herbario_nacional.models.location.City
import com.example.herbario_nacional.models.location.State
import com.example.herbario_nacional.repo.CityRepository
import com.example.herbario_nacional.repo.StateRepository
import com.example.herbario_nacional.ui.Event
import kotlinx.coroutines.launch

class CityViewModel (private val cityRepository: CityRepository): ViewModel() {

    private val _uiState = MutableLiveData<CityDataState>()

    val uiState: LiveData<CityDataState>
        get() = _uiState

    init {
        requestCity()
    }

    fun requestCity() {
        viewModelScope.launch {
            runCatching {
                emitUiState(showProgress = true)
                cityRepository.getCities()
            }.onSuccess {
                emitUiState(result = Event(it))
            }.onFailure {
                emitUiState(error = Event(R.string.internet_connection_error))
            }
        }
    }

    fun emitUiState(
        showProgress: Boolean = false,
        result: Event<List<City>>? = null,
        error: Event<Int>? = null){
        val dataState = CityDataState(
            showProgress,
            result,
            error
        )
        _uiState.value = dataState
    }

    data class CityDataState(
        val showProgress: Boolean,
        val result: Event<List<City>>?,
        val error: Event<Int>?
    )
}