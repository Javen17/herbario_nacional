package com.example.herbario_nacional.viewHolder

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.herbario_nacional.R
import com.example.herbario_nacional.base.BaseApplication
import com.example.herbario_nacional.imageloader.ImageLoader
import com.example.herbario_nacional.models.funghi.FunghiSpecimen
import com.example.herbario_nacional.ui.Activities.DataSheetInformationFungus
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fungus_content.*

class FungusViewHolder constructor(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer{

    @SuppressLint("SetTextI18n")
    fun bind(funghi: FunghiSpecimen, imageLoader: ImageLoader) {
        fungus_content.setOnClickListener {
            showActivity(DataSheetInformationFungus::class.java, funghi)
        }

        fungusImage?.let { imageLoader.load(/*"${BuildConfig.HERBARIO_URL}/gallery/${plant.image}"*/ "https://source.unsplash.com/random", it) }
        fungusName.text = funghi.species.common_name
        fungusFamily.text = funghi.family.name
        username.text = "${funghi.user.first_name}, ${funghi.user.last_name}"
        registration_date.text = funghi.date_received
    }


    companion object {
        fun create(parent: ViewGroup): FungusViewHolder {
            return FungusViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fungus_card, parent, false))
        }
    }

    private fun showActivity(activityClass: Class<*>, funghi: FunghiSpecimen) {
        val intent = Intent(BaseApplication.context, activityClass)

        intent.putExtra("commonName", funghi.species.common_name)
        intent.putExtra("family", funghi.family.name)
        intent.putExtra("genus", funghi.species.genus.name)
        intent.putExtra("specie", funghi.species.scientific_name)
        intent.putExtra("habitat", funghi.ecosystem.name)
        intent.putExtra("habitatDescription", funghi.recolection_area_status.name)
        intent.putExtra("location", "${funghi.city.name}, ${funghi.country.name}")
        intent.putExtra("specificLocation", funghi.location)
        intent.putExtra("date", funghi.date_received)
        intent.putExtra("recolector", "${funghi.user.first_name} ${funghi.user.last_name}")
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        BaseApplication.context.startActivity(intent)
    }
}