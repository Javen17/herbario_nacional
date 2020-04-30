package com.example.herbario_nacional.ui.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.herbario_nacional.R
import com.example.herbario_nacional.models.Genus
import com.example.herbario_nacional.models.Me
import com.example.herbario_nacional.models.MeData
import com.example.herbario_nacional.repo.GenusRepository
import com.example.herbario_nacional.repo.MeRepository
import com.example.herbario_nacional.ui.Event
import kotlinx.coroutines.launch
import okhttp3.RequestBody

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

    fun updateAccount(data: RequestBody) {
        viewModelScope.launch {
            runCatching {
                //emitUiState(showProgress = true)
                Log.i("ACCOUNT UPDATE ROUTE ", "ACCOUNT: "+data.toString())
                meRepository.updateMe(data)

            }.onSuccess {
                //emitUiState(result = Event(it))
                Log.i("ACCOUNT UPDATE ROUTE ", "ACCOUNT SEEMS TO BE SUCCESSFUL ")
            }.onFailure {
                //emitUiState(error = Event(R.string.unauthenticated))
                Log.i("ACCOUNT UPDATE ROUTE ", "ACCOUNT SEEMS TO HAVE FAILED ")
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