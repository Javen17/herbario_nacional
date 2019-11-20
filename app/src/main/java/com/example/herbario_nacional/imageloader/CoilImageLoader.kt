package com.example.herbario_nacional.imageloader

import android.widget.ImageView
import coil.api.load
import coil.request.CachePolicy
import com.example.herbario_nacional.R

class CoilImageLoader : ImageLoader {
    override fun load(imageUrl: String, imageView: ImageView) {
        imageView.load(imageUrl){
            crossfade(true)
            error(R.drawable.ic_launcher_background)
            placeholder(R.drawable.ic_launcher_background)
            memoryCachePolicy(CachePolicy.ENABLED)
        }
    }
}