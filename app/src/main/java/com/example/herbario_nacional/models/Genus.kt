package com.example.herbario_nacional.models

import androidx.annotation.Keep

@Keep
data class Genus(
    val name: String,
    val family: PlantFamily,
    val type: String
)