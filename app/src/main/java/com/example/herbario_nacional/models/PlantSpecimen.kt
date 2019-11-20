package com.example.herbario_nacional.models

import androidx.annotation.Keep
import com.example.herbario_nacional.models.location.City
import com.example.herbario_nacional.models.location.Country
import com.example.herbario_nacional.models.location.State

@Keep
data class PlantSpecimen(
    val user: String,
    val photoURL: String,
    val dateReceived: String,
    val family: PlantFamily,
    val genus: Genus,
    val species: PlantSpecies,
    val status: Status,
    val numberOfSamples: Int,
    val description: String,
    val ecosystem: Ecosystem,
    val recolectionAreaStatus: RecolectionAreaStatus,
    val country: Country,
    val state: State,
    val city: City,
    val latitude: Double,
    val longitude: Double,
    val location: String
)




//user
//photo
//date_received
//family
//genus
//species
//status
//number_of_samples
//description
//ecosystem
//recolection_area_status
//country
//state
//city
//latitude
//longitude
//location