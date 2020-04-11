package com.example.herbario_nacional.models

import androidx.annotation.Keep

@Keep
data class Genus(
    val id: Int,
    val name: String,
    val family: PlantFamily
)