package com.example.herbario_nacional.models.funghi

import androidx.annotation.Keep

@Keep
data class FunghiSpecimen(
    val capType: CapType,
    val shapes: ShapeType,
    val crust: Boolean,
    val color: String,
    val changeOfColor: String,
    val smell: String,
    val additionalInfo: String
)

// this class is MushroomSpecimen

//cap
//forms = shapes
//crust
//color
//change_of_color
//smell
//aditional_info
