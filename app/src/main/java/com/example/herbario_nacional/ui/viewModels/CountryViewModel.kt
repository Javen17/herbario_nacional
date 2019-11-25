package com.example.herbario_nacional.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.herbario_nacional.R
import com.example.herbario_nacional.models.countries.Country
import com.example.herbario_nacional.repo.CountryRepository
import com.example.herbario_nacional.ui.Event
import kotlinx.coroutines.launch

class CountryViewModel (private val countryRepository: CountryRepository): ViewModel() {

    private val _uiState = MutableLiveData<CountryDataState>()

    val uiState: LiveData<CountryDataState>
        get() = _uiState

    fun requestCountry() {
        viewModelScope.launch {
            runCatching {
                emitUiState(showProgress = true)
                countryRepository.getCountries()
            }.onSuccess {
                emitUiState(result = Event(it))
            }.onFailure {
                emitUiState(error = Event(R.string.internet_connection_error))
            }
        }
    }

    fun emitUiState(
        showProgress: Boolean = false,
        result: Event<List<Country>>? = null,
        error: Event<Int>? = null){
        val dataState = CountryDataState(
            showProgress,
            result,
            error
        )
        _uiState.value = dataState
    }

    data class CountryDataState(
        val showProgress: Boolean,
        val result: Event<List<Country>>?,
        val error: Event<Int>?)
}