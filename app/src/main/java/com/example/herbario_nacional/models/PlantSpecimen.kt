package com.example.herbario_nacional.models

import androidx.annotation.Keep
import com.example.herbario_nacional.models.countries.Country
import com.example.herbario_nacional.models.location.City
import com.example.herbario_nacional.models.location.State
import com.google.gson.annotations.SerializedName

@Keep
data class PlantSpecimen(
    val id: Int,
    val user: Register,
    val photo: String?,
    val photo_url: String?,
    val date_received: String,
//     val family: PlantFamily,
//     val genus: Genus,
    val species: PlantSpecies,
    val status: Status,
    val number_of_samples: Int,
    val biostatus: Biostatus,
    val description: String,
    val ecosystem: Ecosystem,
    val recolection_area_status: RecolectionAreaStatus,
    val country: Country,
    val state: State,
    val city: City,
    val latitude: Double?,
    val longitude: Double?,
    val location: String
)