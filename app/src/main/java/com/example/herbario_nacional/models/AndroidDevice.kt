package com.example.herbario_nacional.models

import androidx.annotation.Keep

@Keep
data class AndroidDevice(
    val user: Int = 0,
    val name: String = "",
    val active: Boolean,
    val date_create: String = "",
    val registration_id: String = "",
    val cloud_message_type: String = ""
)