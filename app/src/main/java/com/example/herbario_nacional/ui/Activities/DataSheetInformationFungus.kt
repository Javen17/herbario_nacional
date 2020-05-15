package com.example.herbario_nacional.ui.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coil.api.load
import com.example.herbario_nacional.R
import kotlinx.android.synthetic.main.activity_data_sheet_information_fungus.*
import org.ocpsoft.prettytime.PrettyTime
import java.text.SimpleDateFormat
import java.util.*

class DataSheetInformationFungus : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_sheet_information_fungus)

        val dateReceived: Date? = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).parse(intent.getStringExtra("date")!!)
        val timeAgo = PrettyTime(Locale("es"))

        fungusPicture.load(intent.getStringExtra("photo"))
        commonName.text = intent.getStringExtra("commonName")
        scientificName.text = intent.getStringExtra("scientificName")
        family.text = intent.getStringExtra("family")
        genus.text = intent.getStringExtra("genus")
        specie.text = intent.getStringExtra("specie")
        cap.text = intent.getStringExtra("cap")
        form.text = intent.getStringExtra("form")
        crust.text = intent.getStringExtra("crust")
        color.text = intent.getStringExtra("color")
        changeOfColor.text = intent.getStringExtra("changeOfColor")
        smell.text = intent.getStringExtra("smell")
        fungusDescription.text = intent.getStringExtra("fungusDescription")
        habitat.text = intent.getStringExtra("habitat")
        habitatDescription.text = intent.getStringExtra("habitatDescription")
        location.text = intent.getStringExtra("location")
        specificLocation.text = intent.getStringExtra("specificLocation")
        coordinates.text =  intent.getStringExtra("coordinates")
        date.text = timeAgo.format(dateReceived)
        recolector.text = intent.getStringExtra("recolector")

        close_btn.setOnClickListener { showActivity(MainActivity::class.java) }
    }

    private fun showActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        this.finish()
    }
}
