package com.example.herbario_nacional.viewHolder

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.herbario_nacional.R
import com.example.herbario_nacional.base.BaseApplication
import com.example.herbario_nacional.imageloader.ImageLoader
import com.example.herbario_nacional.models.PlantSpecimen
import com.example.herbario_nacional.models.funghi.FunghiSpecimen
import com.example.herbario_nacional.ui.Activities.DataSheetInformation
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fungus_content.*
import kotlinx.android.synthetic.main.fungus_content.country
import kotlinx.android.synthetic.main.fungus_content.plantFamily
import kotlinx.android.synthetic.main.fungus_content.registration_date
import kotlinx.android.synthetic.main.fungus_content.username
import kotlinx.android.synthetic.main.plant_content.*

class FungusViewHolder constructor(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer{

    fun bind(funghi: FunghiSpecimen, imageLoader: ImageLoader) {
        fungus_content.setOnClickListener {
            showActivity(DataSheetInformation::class.java, funghi)
        }

        plantImage?.let { imageLoader.load(/*"${BuildConfig.HERBARIO_URL}/gallery/${plant.image}"*/ "https://source.unsplash.com/random", it) }
        plantName.text = "${funghi.capType.name}"
        //plantName.text = "${plant.species.common_name}"
        plantFamily.text = funghi.shapes.name
        //plantFamily.text = plant.family.name

        //username.text = "${funghi.additionalInfo} ${plant.user.last_name}"
        //country.text = "${plant.city.name}, ${plant.country.name}"
        //registration_date.text = plant.date_received
    }


    companion object {
        fun create(parent: ViewGroup): FungusViewHolder {
            return FungusViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fungus_card, parent, false))
        }
    }

    private fun showActivity(activityClass: Class<*>, funghi: FunghiSpecimen) {
        val intent = Intent(BaseApplication.context, activityClass)
        intent.putExtra("commonName", funghi.capType.name)
        intent.putExtra("color", funghi.shapes.name)
        intent.putExtra("changeOfColor", funghi.additionalInfo)
        //intent.putExtra("commonName", plant.species.common_name)
        //intent.putExtra("family", plant.family.name)
        //intent.putExtra("genus", plant.species.genus.name)
        //intent.putExtra("specie", plant.species.scientific_name)
        //intent.putExtra("habitat", plant.ecosystem.name)
        //intent.putExtra("habitatDescription", plant.recolection_area_status.name)
        //intent.putExtra("biostatus", plant.biostatus.name)
        //intent.putExtra("location", "${plant.city.name}, ${plant.country.name}")
        //intent.putExtra("specificLocation", plant.location)
        //intent.putExtra("date", plant.date_received)
        //intent.putExtra("recolector", "${plant.user.first_name} ${plant.user.last_name}")
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        BaseApplication.context.startActivity(intent)
    }
}