package com.example.herbario_nacional.models

import androidx.annotation.Keep

@Keep
data class AndroidDevice(
    val application_id: String? = "",
    val id: Int = 0,
    val user: Int = 0,
    val name: String = "",
    val active: Boolean = true,
    val date_create: String = "",
    val device_id: String? = "",
    val registration_id: String = "",
    val cloud_message_type: String = ""
)