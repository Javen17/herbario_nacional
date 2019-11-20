package com.example.herbario_nacional.models

import androidx.annotation.Keep

@Keep
data class Register(
    val first_name: String = "",
    val last_name: String = "",
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val is_staff: Boolean = false,
    val is_active: Boolean = true,
    val is_superuser: Boolean = false,
    val date_joined: String? = null,
    val name: String = first_name,
    val groups: Array<String?> = arrayOf(),
    val user_permissions: Array<String?> = arrayOf(),
    val last_login: String? = null
)