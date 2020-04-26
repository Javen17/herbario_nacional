package com.example.herbario_nacional.util

import android.location.Location.FORMAT_SECONDS
import android.location.Location.convert
import kotlin.math.abs

class Location {
    companion object {
        fun convert(latitude: Double, longitude: Double): String? {
            val builder = StringBuilder()
            if (latitude < 0) {
                builder.append("S ")
            } else {
                builder.append("N ")
            }
            val latitudeDegrees: String = convert(
                abs(latitude),
                FORMAT_SECONDS
            )
            val latitudeSplit = latitudeDegrees.split(":").toTypedArray()
            builder.append(latitudeSplit[0])
            builder.append("°")
            builder.append(latitudeSplit[1])
            builder.append("'")
            builder.append(latitudeSplit[2])
            builder.append("\"")
            builder.append(" ")
            if (longitude < 0) {
                builder.append("O ")
            } else {
                builder.append("E ")
            }
            val longitudeDegrees: String = convert(
                abs(longitude),
                FORMAT_SECONDS
            )
            val longitudeSplit = longitudeDegrees.split(":").toTypedArray()
            builder.append(longitudeSplit[0])
            builder.append("°")
            builder.append(longitudeSplit[1])
            builder.append("'")
            builder.append(longitudeSplit[2])
            builder.append("\"")
            return builder.toString()
        }
    }
}