package com.example.herbario_nacional.viewHolder

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.herbario_nacional.R
import com.example.herbario_nacional.base.BaseApplication.Companion.context
import com.example.herbario_nacional.imageloader.ImageLoader
import com.example.herbario_nacional.models.PlantSpecimen
import com.example.herbario_nacional.ui.Activities.DataSheetInformationPlant
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.plant_content.*
import org.ocpsoft.prettytime.PrettyTime
import java.text.SimpleDateFormat
import java.util.*

class PlantViewHolder constructor(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(plant: PlantSpecimen, imageLoader: ImageLoader) {
        plant_content.setOnClickListener {
            showActivity(DataSheetInformationPlant::class.java, plant)
        }

        val dateReceived: Date? = SimpleDateFormat("yyyy-MM-dd", Locale("es")).parse(plant.date_received)
        val timeAgo = PrettyTime(Locale("es"))

        plantImage?.let {
            imageLoader.load("https://source.unsplash.com/random", it)
        }

        profilePicture?.let {
            imageLoader.load("https://api.adorable.io/avatars/50/12@adorable.png", it)
        }

        plantName.text = plant.species.common_name
        plantFamily.text = plant.species.genus.family.name
        username.text = "${plant.user.first_name} ${plant.user.last_name}"
        country.text = "${plant.city.name}, ${plant.city.state.country.name}"
        registrationDate.text = timeAgo.format(dateReceived)
    }

    companion object {
        fun create(parent: ViewGroup): PlantViewHolder {
            return PlantViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.plant_card, parent, false))
        }
    }

    private fun showActivity(activityClass: Class<*>, plant: PlantSpecimen) {
        val intent = Intent(context, activityClass)
        intent.putExtra("commonName", plant.species.common_name)
        intent.putExtra("scientificName", plant.species.scientific_name)
        intent.putExtra("family", plant.species.genus.family.name)
        intent.putExtra("genus", plant.species.genus.name)
        intent.putExtra("specie", plant.species.scientific_name)
        intent.putExtra("plantDescription", plant.description)
        intent.putExtra("habitat", plant.ecosystem.name)
        intent.putExtra("habitatDescription", plant.recolection_area_status.name)
        intent.putExtra("biostatus", plant.biostatus.name)
        intent.putExtra("location", "${plant.city.name}, ${plant.city.state.country.name}")
        intent.putExtra("specificLocation", plant.location)
        intent.putExtra("date", plant.date_received)
        intent.putExtra("recolector", "${plant.user.first_name} ${plant.user.last_name}")
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }
}