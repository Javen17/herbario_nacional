package com.example.herbario_nacional.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.herbario_nacional.R
import com.example.herbario_nacional.models.funghi.ShapeType
import com.example.herbario_nacional.repo.FormTypeRepository
import com.example.herbario_nacional.ui.Event
import kotlinx.coroutines.launch

class FormTypeViewModel (private val formTypeRepository: FormTypeRepository): ViewModel() {

    private val _uiState = MutableLiveData<FormTypeDataState>()

    val uiState: LiveData<FormTypeDataState>
        get() = _uiState

    init {
        requestFormType()
    }

    fun requestFormType() {
        viewModelScope.launch {
            runCatching {
                emitUiState(showProgress = true)
                formTypeRepository.getFormType()
            }.onSuccess {
                emitUiState(result = Event(it))
            }.onFailure {
                emitUiState(error = Event(R.string.internet_connection_error))
            }
        }
    }

    fun emitUiState(
        showProgress: Boolean = false,
        result: Event<List<ShapeType>>? = null,
        error: Event<Int>? = null){
        val dataState = FormTypeDataState(
            showProgress,
            result,
            error
        )
        _uiState.value = dataState
    }

    data class FormTypeDataState(
        val showProgress: Boolean,
        val result: Event<List<ShapeType>>?,
        val error: Event<Int>?)
}