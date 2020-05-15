package com.example.herbario_nacional.models

import androidx.annotation.Keep

@Keep
data class PostPlantSpecimen(
    val user: Int,
    val photo: String?,
    val date_received: String,
    val biostatus: Int,
    // val family: Int,
    // val genus: Int,
    val species: Int,
    val complete: Boolean,
    val status: Int,
    val number_of_samples: Int,
    val description: String,
    val ecosystem: Int,
    val recolection_area_status: Int,
    // val country: Int,
    // val state: Int,
    val city: Int,
    val latitude: Double?,
    val longitude: Double?,
    val location: String
)