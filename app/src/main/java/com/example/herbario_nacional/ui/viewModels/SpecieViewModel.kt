package com.example.herbario_nacional.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.herbario_nacional.R
import com.example.herbario_nacional.models.Genus
import com.example.herbario_nacional.models.PlantFamily
import com.example.herbario_nacional.models.PlantSpecies
import com.example.herbario_nacional.repo.FamilyRepository
import com.example.herbario_nacional.repo.GenusRepository
import com.example.herbario_nacional.repo.SpecieRepository
import com.example.herbario_nacional.ui.Event
import kotlinx.coroutines.launch

class SpecieViewModel (private val specieRepository: SpecieRepository): ViewModel() {

    private val _uiState = MutableLiveData<SpecieDataState>()

    val uiState: LiveData<SpecieDataState>
        get() = _uiState

    init {
        requestSpecie()
    }

    fun requestSpecie() {
        viewModelScope.launch {
            runCatching {
                emitUiState(showProgress = true)
                specieRepository.getSpecies()
            }.onSuccess {
                emitUiState(result = Event(it))
            }.onFailure {
                emitUiState(error = Event(R.string.internet_connection_error))
            }
        }
    }

    fun emitUiState(
        showProgress: Boolean = false,
        result: Event<List<PlantSpecies>>? = null,
        error: Event<Int>? = null){
        val dataState = SpecieDataState(
            showProgress,
            result,
            error
        )
        _uiState.value = dataState
    }

    data class SpecieDataState(
        val showProgress: Boolean,
        val result: Event<List<PlantSpecies>>?,
        val error: Event<Int>?)
}