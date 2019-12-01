package com.example.herbario_nacional.ui.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import com.example.herbario_nacional.R
import com.example.herbario_nacional.ui.viewModels.CountryViewModel
import com.example.herbario_nacional.ui.viewModels.FamilyViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_new_plant.*
import kotlinx.android.synthetic.main.new_plant.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class NewPlantActivity : AppCompatActivity() {

    private val countryViewModel: CountryViewModel by viewModel()
    private val familyViewModel: FamilyViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_plant)

        familyViewModel.uiState.observe(this, Observer {
            val dataState = it ?: return@Observer
            if (dataState.result != null && !dataState.result.consumed){
                dataState.result.consume()?.let { result ->
                    println("Nombre de familia: ${result[1].name}")
                }
            }
            if (dataState.error != null && !dataState.error.consumed){
                dataState.error.consume()?.let { error ->
                    Toast.makeText(applicationContext, resources.getString(error), Toast.LENGTH_LONG).show()
                }
            }
        })

        countryViewModel.uiState.observe(this, Observer {
            val dataState = it ?: return@Observer
            if (dataState.result != null && !dataState.result.consumed){
                dataState.result.consume()?.let { result ->
                    println("Nombre del país: ${result[1].name}")
                }
            }
            if (dataState.error != null && !dataState.error.consumed){
                dataState.error.consume()?.let { error ->
                    Toast.makeText(applicationContext, resources.getString(error), Toast.LENGTH_LONG).show()
                }
            }
        })

        cancel_btn.setOnClickListener {
            showActivity(MainActivity::class.java)
        }

        register_btn.setOnClickListener {

        }

        add_a_photo.setOnClickListener {
            showDialog()
        }

        val newPlant: ConstraintLayout = findViewById(R.id.new_plant)
        val bottomSheetBehavior = BottomSheetBehavior.from(newPlant)

        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun showDialog(){
        val array = arrayOf("Tomar una foto", "Agregar de la galería")

        val builder = AlertDialog.Builder(this)

        builder.setTitle("Agregar una foto")

        builder.setItems(array) { _, which ->
            val selected = array[which]
            if (selected == "Tomar una foto") {
                val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(takePicture, 0)
            }
            else {
                val pickPhoto = Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
                startActivityForResult(pickPhoto, 1)
            }
        }

        builder.setNegativeButton("Cancelar", null)

        val dialog = builder.create()

        dialog.show()
    }

    private fun showActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        this.finish()
    }
}
