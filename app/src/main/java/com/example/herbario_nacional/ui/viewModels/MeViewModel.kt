package com.example.herbario_nacional.ui.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.herbario_nacional.R
import com.example.herbario_nacional.models.Me
import com.example.herbario_nacional.repo.MeRepository
import com.example.herbario_nacional.ui.Event
import kotlinx.coroutines.launch

class MeViewModel (private val meRepository: MeRepository): ViewModel() {

    private val _uiState = MutableLiveData<MeDataState>()

    val uiState: LiveData<MeDataState>
        get() = _uiState

    init {
        requestMe()
    }

    fun requestMe() {
        viewModelScope.launch {
            runCatching {
                emitUiState(showProgress = true)
                meRepository.getMe()
            }.onSuccess {
                emitUiState(result = Event(it))
            }.onFailure {
                emitUiState(error = Event(R.string.unauthenticated))
            }
        }
    }

    fun emitUiState(
        showProgress: Boolean = false,
        result: Event<Me>? = null,
        error: Event<Int>? = null){
        val dataState = MeDataState(
            showProgress,
            result,
            error
        )
        _uiState.value = dataState
    }

    data class MeDataState(
        val showProgress: Boolean,
        val result: Event<Me>?,
        val error: Event<Int>?)
}