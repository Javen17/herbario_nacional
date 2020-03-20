package com.example.herbario_nacional.models

import androidx.annotation.Keep

@Keep
data class Profile(
    val id: Int,
    val number_id: String,
    val phone: String = "",
    val photo: String? = null,
    val user: Int
)