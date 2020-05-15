package com.example.herbario_nacional.data.network.`interface`

import com.example.herbario_nacional.models.location.State
import retrofit2.http.GET

interface StateInterface {
    @GET("api/state/")
    suspend fun requestState(): List<State>
}