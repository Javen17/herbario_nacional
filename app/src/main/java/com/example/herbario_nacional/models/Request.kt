package com.example.herbario_nacional.models

import androidx.annotation.Keep
import com.example.herbario_nacional.models.location.Country

@Keep
data class Request(
    val respuesta: List<Country>
)