package com.example.herbario_nacional.ui.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coil.api.load
import com.example.herbario_nacional.R
import com.example.herbario_nacional.imageloader.ImageLoader
import kotlinx.android.synthetic.main.activity_data_sheet_information_plant.*

class DataSheetInformationPlant : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_sheet_information_plant)

        plant_picture.load("https://source.unsplash.com/random")
        commonName.text = intent.getStringExtra("commonName")
        scientificName.text = intent.getStringExtra("scientificName")
        family.text = intent.getStringExtra("family")
        genus.text = intent.getStringExtra("genus")
        specie.text = intent.getStringExtra("specie")
        plantDescription.text = intent.getStringExtra("plantDescription")
        habitat.text = intent.getStringExtra("habitat")
        habitatDescription.text = intent.getStringExtra("habitatDescription")
        biostatus.text = intent.getStringExtra("biostatus")
        location.text = intent.getStringExtra("location")
        coordinates.text =  intent.getStringExtra("coordinates")
        specificLocation.text = intent.getStringExtra("specificLocation")
        date.text = intent.getStringExtra("date")
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
