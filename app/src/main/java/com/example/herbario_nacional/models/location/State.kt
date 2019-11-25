package com.example.herbario_nacional.models.location

import androidx.annotation.Keep
import com.example.herbario_nacional.models.countries.Country

@Keep
data class State(var name: String, val country: Country)