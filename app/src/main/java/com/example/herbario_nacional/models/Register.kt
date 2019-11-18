package com.example.herbario_nacional.models

import androidx.annotation.Keep

@Keep
data class Register(
    val first_name: String = "",
    val last_name: String = "",
    val cellphone_number: String = "",
    val reference_number: String = "",
    val username: String = "",
    val email: String = "",
    val password: String = ""
)

