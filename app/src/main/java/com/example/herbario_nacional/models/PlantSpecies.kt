package com.example.herbario_nacional.models

import androidx.annotation.Keep

@Keep
data class PlantSpecies(
    val id: Int,
    val common_name: String,
    val scientific_name: String,
    val genus: Genus,
    val description: String,
    val photo: String
)