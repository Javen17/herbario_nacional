package com.example.herbario_nacional.ui.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coil.api.load
import com.example.herbario_nacional.R
import com.example.herbario_nacional.imageloader.ImageLoader
import kotlinx.android.synthetic.main.activity_data_sheet_information.*

class DataSheetInformation : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_sheet_information)

        plant_picture.load("https://source.unsplash.com/random")
        commonName.text = intent.getStringExtra("commonName")
        family.text = intent.getStringExtra("family")
        genus.text = intent.getStringExtra("genus")
        specie.text = intent.getStringExtra("specie")
        habitat.text = intent.getStringExtra("habitat")
        habitatDescription.text = intent.getStringExtra("habitatDescription")
        biostatus.text = intent.getStringExtra("biostatus")
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
