package com.example.herbario_nacional.models.funghi

import androidx.annotation.Keep

@Keep
data class PostFungusSpecimen(
    val user: Int,
    val photo: String?,
    val date_received: String,
    val biostatus: Int,
    val family: Int,
    val capType: Int,
    val shapes: Int,
    val crust: Int,
    val color: Int,
    val changeOfColor: String,
    val genus: Int,
    val species: Int,
    val complete: Boolean,
    val status: Int,
    val number_of_samples: Int,
    val smell: Int,
    val description: String,
    val ecosystem: Int,
    val recolection_area_status: Int,
    val country: Int,
    val state: Int,
    val city: Int,
    val latitude: Double?,
    val additionalInfo: String,
    val longitude: Double?,
    val location: String
)