package com.example.herbario_nacional.models

import androidx.annotation.Keep

@Keep
data class Profile(
    val id: Int,
    val number_id: String,
    val phone: String = "",
    val photo: String?,
    val photo_url: String?,
    val user: Int
)