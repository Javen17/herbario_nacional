package com.example.herbario_nacional.imageloader

import android.widget.ImageView

interface ImageLoader {
    fun load(imageUrl: String, imageView: ImageView)
}