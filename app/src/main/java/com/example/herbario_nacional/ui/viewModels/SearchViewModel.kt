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
import java.io.PrintWriter
import java.io.StringWriter

class SearchViewModel (private val plantRepository: PlantRepository): ViewModel() {

    private val _uiState = MutableLiveData<PlantDataState>()

    val categoryList: ArrayList<String> = ArrayList()
    val optionIdItemPosition = MutableLiveData<Int>()
    var optionIdValue
        get() = optionIdItemPosition.value?.let {
            categoryList?.get(it)
        }
        set(value) {
            val position = categoryList?.indexOfFirst {
                it.equals(value)
            } ?: -1
            if (position != -1) {
                optionIdItemPosition.value = position + 1
            }
        }
    val categoryIdItem
        get() =
            optionIdItemPosition.value?.let {
                categoryList?.get(it)
            }

    init {
        if (categoryList.isEmpty()) categoryList.addAll(arrayListOf("Hongos", "Plantas"))
    }

    val uiState: LiveData<PlantDataState>
        get() = _uiState

    fun searchPlantByName(value: String) {
        viewModelScope.launch {
            runCatching {
                emitUiState(showProgress = true)
                plantRepository.searchByName(value)
            }.onSuccess {
                emitUiState(result = Event(it))
            }.onFailure {
                val sw = StringWriter()
                it.printStackTrace(PrintWriter(sw))
                val exceptionAsString = sw.toString()
                println(exceptionAsString)
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