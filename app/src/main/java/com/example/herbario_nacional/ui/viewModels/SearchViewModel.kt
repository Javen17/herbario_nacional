package com.example.herbario_nacional.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.herbario_nacional.R
import com.example.herbario_nacional.models.Plant
import com.example.herbario_nacional.models.PlantSpecimen
import com.example.herbario_nacional.models.funghi.FunghiSpecimen
import com.example.herbario_nacional.repo.FungusRepository
import com.example.herbario_nacional.repo.PlantRepository
import com.example.herbario_nacional.ui.Event
import kotlinx.coroutines.launch
import java.io.PrintWriter
import java.io.StringWriter

class SearchViewModel (private val plantRepository: PlantRepository, private val fungusRepository: FungusRepository): ViewModel() {

    private val _plantState = MutableLiveData<PlantDataState>()

    val plantState: LiveData<PlantDataState>
        get() = _plantState

    private val _funghiState = MutableLiveData<FunghiDataState>()

    val funghiState: LiveData<FunghiDataState>
        get() = _funghiState

//    plant methods

    fun searchPlantByName(value: String) {
        viewModelScope.launch {
            runCatching {
                emitPlantState(showProgress = true)
                plantRepository.searchByName(value)
            }.onSuccess {
                emitPlantState(result = Event(it))
            }.onFailure {
                val sw = StringWriter()
                it.printStackTrace(PrintWriter(sw))
                val exceptionAsString = sw.toString()
                println(exceptionAsString)
                emitPlantState(error = Event(R.string.internet_connection_error))
            }
        }
    }

    fun searchPlantByFamily(value: String) {
        viewModelScope.launch {
            runCatching {
                emitPlantState(showProgress = true)
                plantRepository.searchByFamily(value)
            }.onSuccess {
                emitPlantState(result = Event(it))
            }.onFailure {
                val sw = StringWriter()
                it.printStackTrace(PrintWriter(sw))
                val exceptionAsString = sw.toString()
                println(exceptionAsString)
                emitPlantState(error = Event(R.string.internet_connection_error))
            }
        }
    }

    fun searchPlantByGenus(value: String) {
        viewModelScope.launch {
            runCatching {
                emitPlantState(showProgress = true)
                plantRepository.searchByGenus(value)
            }.onSuccess {
                emitPlantState(result = Event(it))
            }.onFailure {
                val sw = StringWriter()
                it.printStackTrace(PrintWriter(sw))
                val exceptionAsString = sw.toString()
                println(exceptionAsString)
                emitPlantState(error = Event(R.string.internet_connection_error))
            }
        }
    }

//    funghi methods

    fun searchFungusByName(value: String) {
        viewModelScope.launch {
            runCatching {
                emitFunghiState(showProgress = true)
                fungusRepository.searchByName(value)
            }.onSuccess {
                emitFunghiState(result = Event(it))
            }.onFailure {
                val sw = StringWriter()
                it.printStackTrace(PrintWriter(sw))
                val exceptionAsString = sw.toString()
                println(exceptionAsString)
                emitFunghiState(error = Event(R.string.internet_connection_error))
            }
        }
    }

    fun searchFungusByFamily(value: String) {
        viewModelScope.launch {
            runCatching {
                emitFunghiState(showProgress = true)
                fungusRepository.searchByFamily(value)
            }.onSuccess {
                emitFunghiState(result = Event(it))
            }.onFailure {
                val sw = StringWriter()
                it.printStackTrace(PrintWriter(sw))
                val exceptionAsString = sw.toString()
                println(exceptionAsString)
                emitFunghiState(error = Event(R.string.internet_connection_error))
            }
        }
    }

    fun searchFungusByGenus(value: String) {
        viewModelScope.launch {
            runCatching {
                emitFunghiState(showProgress = true)
                fungusRepository.searchByGenus(value)
            }.onSuccess {
                emitFunghiState(result = Event(it))
            }.onFailure {
                val sw = StringWriter()
                it.printStackTrace(PrintWriter(sw))
                val exceptionAsString = sw.toString()
                println(exceptionAsString)
                emitFunghiState(error = Event(R.string.internet_connection_error))
            }
        }
    }

//  state emitters

    private fun emitPlantState(
        showProgress: Boolean = false,
        result: Event<MutableList<PlantSpecimen>>? = null,
        error: Event<Int>? = null) {

        val dataState = PlantDataState(
            showProgress,
            result,
            error
        )

        _plantState.value = dataState
    }

    private fun emitFunghiState(
        showProgress: Boolean = false,
        result: Event<MutableList<FunghiSpecimen>>? = null,
        error: Event<Int>? = null) {

        val dataState = FunghiDataState(
            showProgress,
            result,
            error
        )

        _funghiState.value = dataState
    }

//    state classes

    data class PlantDataState(
        val showProgress: Boolean,
        val result: Event<MutableList<PlantSpecimen>>?,
        val error: Event<Int>?
    )

    data class FunghiDataState(
        val showProgress: Boolean,
        val result: Event<MutableList<FunghiSpecimen>>?,
        val error: Event<Int>?
    )

}