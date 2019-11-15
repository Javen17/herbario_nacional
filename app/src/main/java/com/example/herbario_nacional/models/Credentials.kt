package com.example.herbario_nacional.models

import androidx.annotation.Keep

@Keep
data class Credentials(
    val password: String = "", // admin
    val username: String = "" // admin
)