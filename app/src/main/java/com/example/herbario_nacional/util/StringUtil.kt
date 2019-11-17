package com.example.herbario_nacional.util

fun String.isEmailValid() = android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
