package com.example.herbario_nacional.models

import androidx.annotation.Keep

@Keep
data class PlantSpecies(
    val commonName: String,
    val scientificName: String,
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