package com.example.herbario_nacional.models

import androidx.annotation.Keep

@Keep
data class Profile(
    val number_id: Int,
    val phone: String = "",
    val photo: String? = null,
    val user: Int
)