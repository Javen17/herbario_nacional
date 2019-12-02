package com.example.herbario_nacional.viewHolder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.herbario_nacional.BuildConfig
import com.example.herbario_nacional.R
import com.example.herbario_nacional.imageloader.ImageLoader
import com.example.herbario_nacional.models.Plant
import com.example.herbario_nacional.models.PlantSpecimen
import com.example.herbario_nacional.util.ImageSizer
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.plant_content.*

class PlantViewHolder constructor(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(plant: PlantSpecimen, imageLoader: ImageLoader) {
        plantImage?.let { imageLoader.load(/*"${BuildConfig.HERBARIO_URL}/gallery/${plant.image}"*/ "https://source.unsplash.com/random", it) }
        plantName.text = plant.species.common_name
        username.text = "${plant.user.first_name} ${plant.user.last_name}"
        country.text = plant.country.name
        registration_date.text = plant.date_received
    }

    companion object {
        fun create(parent: ViewGroup): PlantViewHolder {
            return PlantViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.plant_card, parent, false))
        }
    }
}