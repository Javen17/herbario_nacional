package com.example.herbario_nacional.repo

import com.example.herbario_nacional.data.network.`interface`.StateInterface
import com.example.herbario_nacional.models.location.State

interface StateRepository {
    suspend fun getStates(): List<State>
}

class StateRepositoryImpl(private val stateService: StateInterface): StateRepository{
    override suspend fun getStates(): List<State> {
        return stateService.requestState()
    }
}