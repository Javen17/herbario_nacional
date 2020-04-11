package com.example.herbario_nacional.viewHolder

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.herbario_nacional.R
import com.example.herbario_nacional.base.BaseApplication.Companion.context
import com.example.herbario_nacional.imageloader.ImageLoader
import com.example.herbario_nacional.models.funghi.FunghiSpecimen
import com.example.herbario_nacional.ui.Activities.DataSheetInformationFungus
import com.example.herbario_nacional.util.Location
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fungus_content.*
import org.ocpsoft.prettytime.PrettyTime
import java.text.SimpleDateFormat
import java.util.*

class FungusViewHolder constructor(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
    fun bind(funghi: FunghiSpecimen, imageLoader: ImageLoader) {
        fungus_content.setOnClickListener {
            showActivity(DataSheetInformationFungus::class.java, funghi)
        }
        val dateReceived: Date? = SimpleDateFormat("yyyy-MM-dd", Locale("es")).parse(funghi.date_received)
        val timeAgo = PrettyTime(Locale("es"))

        fungusImage?.let {
            imageLoader.load("https://source.unsplash.com/random", it)
        }
        profilePicture?.let {
            imageLoader.load("https://api.adorable.io/avatars/50/12@adorable.png", it)
        }
        fungusName.text =funghi.species.common_name
        fungusFamily.text = funghi.species.genus.family.name
        username.text = "${funghi.user.first_name} ${funghi.user.last_name}"
        country.text = "${funghi.city.name}, ${funghi.city.state.country.name}"
        registrationDate.text = timeAgo.format(dateReceived)
    }

    companion object {
        fun create(parent: ViewGroup): FungusViewHolder {
            return FungusViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fungus_card, parent, false))
        }
    }

    private fun showActivity(activityClass: Class<*>, funghi: FunghiSpecimen) {
        val intent = Intent(context, activityClass)
        intent.putExtra("commonName", funghi.species.common_name)
        intent.putExtra("scientificName", funghi.species.scientific_name)
        intent.putExtra("family", funghi.species.genus.family.name)
        intent.putExtra("genus", funghi.species.genus.name)
        intent.putExtra("specie", funghi.species.scientific_name)
        intent.putExtra("cap", funghi.cap.name)
        intent.putExtra("form", funghi.forms.name)
        intent.putExtra("crust", if (funghi.crust) "Sí" else "No")
        intent.putExtra("color", funghi.color)
        intent.putExtra("changeOfColor", funghi.change_of_color)
        intent.putExtra("smell", funghi.smell)
        intent.putExtra("fungusDescription", funghi.description)
        intent.putExtra("habitat", funghi.ecosystem.name)
        intent.putExtra("habitatDescription", funghi.recolection_area_status.name)
        intent.putExtra("location", "${funghi.city.name}, ${funghi.city.state.country.name}")
        intent.putExtra("specificLocation", funghi.location)
        intent.putExtra("coordinates",
            if(funghi.latitude != null && funghi.longitude != null) {
                Location.convert(funghi.latitude, funghi.longitude)
            } else "Coordenadas no disponibles."
        )

        intent.putExtra("date", funghi.date_received)
        intent.putExtra("recolector", "${funghi.user.first_name} ${funghi.user.last_name}")
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }
}