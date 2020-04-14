package com.example.herbario_nacional.models

import androidx.annotation.Keep

@Keep
data class PlantSpecies(
    val id: Int,
    val name: String,
    val common_name: String,
    val scientific_name: String,
    val family: PlantFamily,
    val genus: Genus,
    val description: String,
    val photoURL: String,
    val type: String
)

//common_name
//scientific_name
//family
//genus
//description
//photo
//type