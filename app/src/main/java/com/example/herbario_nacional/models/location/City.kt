package com.example.herbario_nacional.models.location

import androidx.annotation.Keep

@Keep
data class City(val id: Int, val name: String, val state: State)