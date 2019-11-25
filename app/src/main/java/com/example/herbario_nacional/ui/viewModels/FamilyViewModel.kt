package com.example.herbario_nacional.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.herbario_nacional.R
import com.example.herbario_nacional.models.PlantFamily
import com.example.herbario_nacional.repo.FamilyRepository
import com.example.herbario_nacional.ui.Event
import kotlinx.coroutines.launch

class FamilyViewModel (private val familyRepository: FamilyRepository): ViewModel() {

    private val _uiState = MutableLiveData<FamilyDataState>()

    val uiState: LiveData<FamilyDataState>
        get() = _uiState

    fun requestFamily() {
        viewModelScope.launch {
            runCatching {
                emitUiState(showProgress = true)
                familyRepository.getFamilies()
            }.onSuccess {
                emitUiState(result = Event(it))
            }.onFailure {
                emitUiState(error = Event(R.string.internet_connection_error))
            }
        }
    }

    fun emitUiState(
        showProgress: Boolean = false,
        result: Event<List<PlantFamily>>? = null,
        error: Event<Int>? = null){
        val dataState = FamilyDataState(
            showProgress,
            result,
            error
        )
        _uiState.value = dataState
    }

    data class FamilyDataState(
        val showProgress: Boolean,
        val result: Event<List<PlantFamily>>?,
        val error: Event<Int>?)
}