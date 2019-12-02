package com.example.herbario_nacional.ui.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import com.example.herbario_nacional.R
import com.example.herbario_nacional.models.PlantSpecimen
import com.example.herbario_nacional.models.PostPlantSpecimen
import com.example.herbario_nacional.ui.viewModels.*
import com.example.herbario_nacional.util.StatusCode
import com.example.herbario_nacional.util.traveseAnyInput
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_new_plant.*
import kotlinx.android.synthetic.main.new_plant.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class NewPlantActivity : AppCompatActivity() {

    private val countryViewModel: CountryViewModel by viewModel()
    private val stateViewModel: StateViewModel by viewModel()
    private val cityViewModel: CityViewModel by viewModel()
    private val familyViewModel: FamilyViewModel by viewModel()
    private val genusViewModel: GenusViewModel by viewModel()
    private val specieViewModel: SpecieViewModel by viewModel()
    private val statusViewModel: StatusViewModel by viewModel()
    private val habitatViewModel: HabitatViewModel by viewModel()
    private val habitatDescriptionViewModel: HabitatDescriptionViewModel by viewModel()
    private val biostatusViewModel: BiostatusViewModel by viewModel()
    private val newPlantViewModel: NewPlantViewModel by viewModel()
    private val plantViewModel: PlantViewModel by viewModel()

    private lateinit var familySpinner: Spinner
    private lateinit var genusSpinner: Spinner
    private lateinit var specieSpinner: Spinner
    private lateinit var plantStatusSpinner: Spinner
    private lateinit var countrySpinner: Spinner
    private lateinit var stateSpinner: Spinner
    private lateinit var citySpinner: Spinner
    private lateinit var habitatSpinner: Spinner
    private lateinit var habitatDescriptionSpinner: Spinner
    private lateinit var biostatusSpinner: Spinner

    val families: ArrayList<String> = ArrayList()
    val genuses: ArrayList<String> = ArrayList()
    val species: ArrayList<String> = ArrayList()
    val status: ArrayList<String> = ArrayList()
    val countries: ArrayList<String> = ArrayList()
    val states: ArrayList<String> = ArrayList()
    val cities: ArrayList<String> = ArrayList()
    val habitats: ArrayList<String> = ArrayList()
    val habitatDescription: ArrayList<String> = ArrayList()
    val biostatus: ArrayList<String> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_plant)

        familySpinner = findViewById(R.id.family)
        genusSpinner = findViewById(R.id.genus)
        specieSpinner = findViewById(R.id.specie)
        plantStatusSpinner = findViewById(R.id.plantStatus)
        countrySpinner = findViewById(R.id.country)
        stateSpinner = findViewById(R.id.state)
        citySpinner = findViewById(R.id.city)
        habitatSpinner = findViewById(R.id.habitat)
        habitatDescriptionSpinner = findViewById(R.id.habitatDescription)
        biostatusSpinner = findViewById(R.id.biostatus)

        familyViewModel.uiState.observe(this, Observer {
            val dataState = it ?: return@Observer
            if (dataState.result != null && !dataState.result.consumed){
                dataState.result.consume()?.let { result ->
                    result.forEach{
                        families.add(it.name)
                    }
                }
            }
            if (dataState.error != null && !dataState.error.consumed){
                dataState.error.consume()?.let { error ->
                    Toast.makeText(applicationContext, resources.getString(error), Toast.LENGTH_LONG).show()
                }
            }
        })

        val familyAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, families)
        familyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        familyAdapter.notifyDataSetChanged()
        familySpinner.adapter = familyAdapter

        genusViewModel.uiState.observe(this, Observer {
            val dataState = it ?: return@Observer
            if (dataState.result != null && !dataState.result.consumed){
                dataState.result.consume()?.let { result ->
                    result.forEach{
                        genuses.add(it.name)
                    }
                }
            }
            if (dataState.error != null && !dataState.error.consumed){
                dataState.error.consume()?.let { error ->
                    Toast.makeText(applicationContext, resources.getString(error), Toast.LENGTH_LONG).show()
                }
            }
        })

        val genusAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genuses)
        genusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genusAdapter.notifyDataSetChanged()
        genusSpinner.adapter = genusAdapter

        specieViewModel.uiState.observe(this, Observer {
            val dataState = it ?: return@Observer
            if (dataState.result != null && !dataState.result.consumed){
                dataState.result.consume()?.let { result ->
                    result.forEach{
                        species.add(it.scientific_name)
                    }
                }
            }
            if (dataState.error != null && !dataState.error.consumed){
                dataState.error.consume()?.let { error ->
                    Toast.makeText(applicationContext, resources.getString(error), Toast.LENGTH_LONG).show()
                }
            }
        })

        val specieAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, species)
        specieAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        specieAdapter.notifyDataSetChanged()
        specieSpinner.adapter = specieAdapter

        statusViewModel.uiState.observe(this, Observer {
            val dataState = it ?: return@Observer
            if (dataState.result != null && !dataState.result.consumed){
                dataState.result.consume()?.let { result ->
                    result.forEach{
                        status.add(it.name)
                    }
                }
            }
            if (dataState.error != null && !dataState.error.consumed){
                dataState.error.consume()?.let { error ->
                    Toast.makeText(applicationContext, resources.getString(error), Toast.LENGTH_LONG).show()
                }
            }
        })

        val statusAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, status)
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        statusAdapter.notifyDataSetChanged()
        plantStatusSpinner.adapter = statusAdapter

        countryViewModel.uiState.observe(this, Observer {
            val dataState = it ?: return@Observer
            if (dataState.result != null && !dataState.result.consumed){
                dataState.result.consume()?.let { result ->
                    result.forEach{
                        countries.add(it.name)
                    }
                }
            }
            if (dataState.error != null && !dataState.error.consumed){
                dataState.error.consume()?.let { error ->
                    Toast.makeText(applicationContext, resources.getString(error), Toast.LENGTH_LONG).show()
                }
            }
        })

        val countryAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, countries)
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        countrySpinner.adapter = countryAdapter

        stateViewModel.uiState.observe(this, Observer {
            val dataState = it ?: return@Observer
            if (dataState.result != null && !dataState.result.consumed){
                dataState.result.consume()?.let { result ->
                    result.forEach{
                        states.add(it.name)
                    }
                }
            }
            if (dataState.error != null && !dataState.error.consumed){
                dataState.error.consume()?.let { error ->
                    Toast.makeText(applicationContext, resources.getString(error), Toast.LENGTH_LONG).show()
                }
            }
        })

        val stateAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, states)
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        stateSpinner.adapter = stateAdapter

        cityViewModel.uiState.observe(this, Observer {
            val dataState = it ?: return@Observer
            if (dataState.result != null && !dataState.result.consumed){
                dataState.result.consume()?.let { result ->
                    result.forEach{
                        cities.add(it.name)
                    }
                }
            }
            if (dataState.error != null && !dataState.error.consumed){
                dataState.error.consume()?.let { error ->
                    Toast.makeText(applicationContext, resources.getString(error), Toast.LENGTH_LONG).show()
                }
            }
        })

        plantViewModel.uiState.observe(this, Observer {
            val dataState = it ?: return@Observer
            if (dataState.result != null && !dataState.result.consumed){
                dataState.result.consume()?.let { result ->
                    result.forEach{
                        println("País: ${it.country}")
                    }
                }
            }
            if (dataState.error != null && !dataState.error.consumed){
                dataState.error.consume()?.let { error ->
                    Toast.makeText(applicationContext, resources.getString(error), Toast.LENGTH_LONG).show()
                }
            }
        })

        val cityAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, cities)
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        citySpinner.adapter = cityAdapter

        habitatViewModel.uiState.observe(this, Observer {
            val dataState = it ?: return@Observer
            if (dataState.result != null && !dataState.result.consumed){
                dataState.result.consume()?.let { result ->
                    result.forEach{
                        habitats.add(it.name)
                    }
                }
            }
            if (dataState.error != null && !dataState.error.consumed){
                dataState.error.consume()?.let { error ->
                    Toast.makeText(applicationContext, resources.getString(error), Toast.LENGTH_LONG).show()
                }
            }
        })

        val habitatAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, habitats)
        habitatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        habitatAdapter.notifyDataSetChanged()
        habitatSpinner.adapter = habitatAdapter

        habitatDescriptionViewModel.uiState.observe(this, Observer {
            val dataState = it ?: return@Observer
            if (dataState.result != null && !dataState.result.consumed){
                dataState.result.consume()?.let { result ->
                    result.forEach{
                        habitatDescription.add(it.name)
                    }
                }
            }
            if (dataState.error != null && !dataState.error.consumed){
                dataState.error.consume()?.let { error ->
                    Toast.makeText(applicationContext, resources.getString(error), Toast.LENGTH_LONG).show()
                }
            }
        })

        val habitatDescriptionAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, habitatDescription)
        habitatDescriptionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        habitatDescriptionAdapter.notifyDataSetChanged()
        habitatDescriptionSpinner.adapter = habitatDescriptionAdapter

        biostatusViewModel.uiState.observe(this, Observer {
            val dataState = it ?: return@Observer
            if (dataState.result != null && !dataState.result.consumed){
                dataState.result.consume()?.let { result ->
                    result.forEach{
                        biostatus.add(it.name)
                    }
                }
            }
            if (dataState.error != null && !dataState.error.consumed){
                dataState.error.consume()?.let { error ->
                    Toast.makeText(applicationContext, resources.getString(error), Toast.LENGTH_LONG).show()
                }
            }
        })

        val biostatusAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, biostatus)
        biostatusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        biostatusAdapter.notifyDataSetChanged()
        biostatusSpinner.adapter = biostatusAdapter

        cancel_btn.setOnClickListener {
            showActivity(MainActivity::class.java)
        }

        register_btn.setOnClickListener {
            val date = Date()
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val currentDate: String = formatter.format(date)

            if (layoutNewPlant.traveseAnyInput()) {
                Toast.makeText(applicationContext, getString(R.string.empty_fields_register), Toast.LENGTH_LONG).show()
            }
            else {
                // Prueba
                newPlantViewModel.requestPostPlant(
                    PostPlantSpecimen(
                        user = 1,
                        photo = null,
                        date_received = currentDate,
                        biostatus = 1,
                        family = 1,
                        genus = 1,
                        species = 1,
                        complete = true,
                        status = 1,
                        number_of_samples = 5,
                        description = "Rosa del monte",
                        ecosystem = 1,
                        recolection_area_status = 1,
                        country = 1,
                        state = 1,
                        city = 1,
                        latitude = null,
                        longitude = null,
                        location = "Nueva Segovia"
                    )
                )

            }
        }

        newPlantViewModel.uiState.observe(this, Observer {
            val dataState = it ?: return@Observer
            if (dataState.status != null && !dataState.status.consumed){
                dataState.status.consume()?.let { status ->
                    println("Estado: $status")
                    if(status.result == "plant added") {
                        Toast.makeText(applicationContext, getString(R.string.sucess), Toast.LENGTH_LONG).show()
                        showActivity(MainActivity::class.java)
                    }
                }
            }

            if (dataState.error != null && !dataState.error.consumed){
                dataState.error.consume()?.let { error ->
                    Toast.makeText(applicationContext, resources.getString(error), Toast.LENGTH_LONG).show()
                }
            }
        })

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
