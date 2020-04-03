package com.example.herbario_nacional.ui.Activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import com.example.herbario_nacional.R
import com.example.herbario_nacional.ui.viewModels.*
import com.example.herbario_nacional.util.traveseAnyInput
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.isapanah.awesomespinner.AwesomeSpinner
import kotlinx.android.synthetic.main.activity_new_plant.*
import kotlinx.android.synthetic.main.new_plant.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.create
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class NewPlantActivity : AppCompatActivity() {
    private val meViewModel: MeViewModel by viewModel()
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

    var currentUser: Int = 0

    val species: ArrayList<String> = ArrayList()
    val specieMap: MutableMap<Int, String> = mutableMapOf()
    var selectedSpecie: Int = 0

    val status: ArrayList<String> = ArrayList()
    val statusMap: MutableMap<Int, String> = mutableMapOf()
    var selectedStatus: Int = 0

    val countries: ArrayList<String> = ArrayList()
    val countryMap: MutableMap<Int, String> = mutableMapOf()
    var selectedCountry: Int = 0

    val states: ArrayList<String> = ArrayList()
    val stateMap: MutableMap<Int, String> = mutableMapOf()
    var selectedState: Int = 0

    val cities: ArrayList<String> = ArrayList()
    val cityMap: MutableMap<Int, String> = mutableMapOf()
    var selectedCity: Int = 0

    val habitats: ArrayList<String> = ArrayList()
    val habitatMap: MutableMap<Int, String> = mutableMapOf()
    var selectedHabitat: Int = 0

    val habitatDescription: ArrayList<String> = ArrayList()
    val habitatDescriptionMap: MutableMap<Int, String> = mutableMapOf()
    var selectedHabitatDescription: Int = 0

    val biostatus: ArrayList<String> = ArrayList()
    val biostatusMap: MutableMap<Int, String> = mutableMapOf()
    var selectedBiostatus: Int = 0

    private lateinit var fileUri: Uri
    private var filePath: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_plant)

        meViewModel.uiState.observe(this, Observer {
            val dataState = it ?: return@Observer
            if (dataState.result != null && !dataState.result.consumed){
                dataState.result.consume()?.let { result ->
                    currentUser = result.data.id
                }
            }
            if (dataState.error != null && !dataState.error.consumed){
                dataState.error.consume()?.let { error ->
                    Toast.makeText(applicationContext, resources.getString(error), Toast.LENGTH_LONG).show()
                }
            }
        })

        specieViewModel.uiState.observe(this, Observer {
            val dataState = it ?: return@Observer
            if (dataState.result != null && !dataState.result.consumed){
                dataState.result.consume()?.let { result ->
                    result.forEach{
                        specieMap[it.id] = it.common_name
                        species.add(it.common_name)
                    }
                }
            }
            if (dataState.error != null && !dataState.error.consumed){
                dataState.error.consume()?.let { error ->
                    Toast.makeText(applicationContext, resources.getString(error), Toast.LENGTH_LONG).show()
                }
            }
        })

        val specieSpinner = findViewById<View>(R.id.specieSpinner) as AwesomeSpinner
        val speciesAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, species)
        specieSpinner.setAdapter(speciesAdapter)

        specieSpinner.setOnSpinnerItemClickListener { _, itemAtPosition ->
            for ((id, value) in specieMap) {
                if (value == itemAtPosition) selectedSpecie = id
            }
        }

        statusViewModel.uiState.observe(this, Observer {
            val dataState = it ?: return@Observer
            if (dataState.result != null && !dataState.result.consumed){
                dataState.result.consume()?.let { result ->
                    result.forEach{
                        statusMap[it.id] = it.name
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

        val statusSpinner = findViewById<View>(R.id.plantStatusSpinner) as AwesomeSpinner
        val statusAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, status)
        statusSpinner.setAdapter(statusAdapter)

        statusSpinner.setOnSpinnerItemClickListener { _, itemAtPosition ->
            for ((id, value) in statusMap) {
                if (value == itemAtPosition) selectedStatus = id
            }
        }

        countryViewModel.uiState.observe(this, Observer {
            val dataState = it ?: return@Observer
            if (dataState.result != null && !dataState.result.consumed){
                dataState.result.consume()?.let { result ->
                    result.forEach{
                        countryMap[it.id] = it.name
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

        val countrySpinner = findViewById<View>(R.id.countrySpinner) as AwesomeSpinner
        val countriesAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countries)
        countrySpinner.setAdapter(countriesAdapter)

        countrySpinner.setOnSpinnerItemClickListener { _, itemAtPosition ->
            for ((id, value) in countryMap) {
                if (value == itemAtPosition) selectedCountry = id
            }
        }

        stateViewModel.uiState.observe(this, Observer {
            val dataState = it ?: return@Observer
            if (dataState.result != null && !dataState.result.consumed){
                dataState.result.consume()?.let { result ->
                    result.forEach{
                        stateMap[it.id] = it.name
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

        val stateSpinner = findViewById<View>(R.id.stateSpinner) as AwesomeSpinner
        val statesAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, states)
        stateSpinner.setAdapter(statesAdapter)

        stateSpinner.setOnSpinnerItemClickListener { _, itemAtPosition ->
            for ((id, value) in stateMap) {
                if (value == itemAtPosition) selectedState = id
            }
        }

        cityViewModel.uiState.observe(this, Observer {
            val dataState = it ?: return@Observer
            if (dataState.result != null && !dataState.result.consumed){
                dataState.result.consume()?.let { result ->
                    result.forEach{
                        cityMap[it.id] = it.name
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

        val citySpinner = findViewById<View>(R.id.citySpinner) as AwesomeSpinner
        val citiesAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cities)
        citySpinner.setAdapter(citiesAdapter)

        citySpinner.setOnSpinnerItemClickListener { _, itemAtPosition ->
            for ((id, value) in cityMap) {
                if (value == itemAtPosition) selectedCity = id
            }
        }
        
        habitatViewModel.uiState.observe(this, Observer {
            val dataState = it ?: return@Observer
            if (dataState.result != null && !dataState.result.consumed){
                dataState.result.consume()?.let { result ->
                    result.forEach{
                        habitatMap[it.id] = it.name
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

        val habitatSpinner = findViewById<View>(R.id.habitatSpinner) as AwesomeSpinner
        val habitatsAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, habitats)
        habitatSpinner.setAdapter(habitatsAdapter)

        habitatSpinner.setOnSpinnerItemClickListener { _, itemAtPosition ->
            for ((id, value) in habitatMap) {
                if (value == itemAtPosition) selectedHabitat = id
            }
        }

        habitatDescriptionViewModel.uiState.observe(this, Observer {
            val dataState = it ?: return@Observer
            if (dataState.result != null && !dataState.result.consumed){
                dataState.result.consume()?.let { result ->
                    result.forEach{
                        habitatDescriptionMap[it.id] = it.name
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

        val habitatDescriptionSpinner = findViewById<View>(R.id.habitatDescriptionSpinner) as AwesomeSpinner
        val habitatDescriptionAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, habitatDescription)
        habitatDescriptionSpinner.setAdapter(habitatDescriptionAdapter)

        habitatDescriptionSpinner.setOnSpinnerItemClickListener { _, itemAtPosition ->
            for ((id, value) in habitatDescriptionMap) {
                if (value == itemAtPosition) selectedHabitatDescription = id
            }
        }

        biostatusViewModel.uiState.observe(this, Observer {
            val dataState = it ?: return@Observer
            if (dataState.result != null && !dataState.result.consumed){
                dataState.result.consume()?.let { result ->
                    result.forEach{
                        biostatusMap[it.id] = it.name
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

        val biostatusSpinner = findViewById<View>(R.id.biostatusSpinner) as AwesomeSpinner
        val biostatusAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, biostatus)
        biostatusSpinner.setAdapter(biostatusAdapter)

        biostatusSpinner.setOnSpinnerItemClickListener { _, itemAtPosition ->
            for ((id, value) in biostatusMap) {
                if (value == itemAtPosition) selectedBiostatus = id
            }
        }

        cancel_btn.setOnClickListener {
            showActivity(MainActivity::class.java)
        }

        register_btn.setOnClickListener {
            postPlantSpecimen()
        }

        newPlantViewModel.uiState.observe(this, Observer {
            val dataState = it ?: return@Observer
            if (dataState.status != null && !dataState.status.consumed){
                dataState.status.consume()?.let { status ->
                    if(status.result == "") {
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
                dispatchTakePictureIntent()
            }
            else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_DENIED){
                        val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                        requestPermissions(permissions, PERMISSION_CODE)
                    }
                    else{
                        pickImageFromGallery()
                    }
                }
                else{
                    pickImageFromGallery()
                }
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

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_PICTURE)
            }
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    companion object {
        private const val REQUEST_IMAGE_PICTURE = 1
        private const val IMAGE_PICK_CODE = 1000
        private const val PERMISSION_CODE = 1001
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery()
                }
                else{
                    Toast.makeText(this, "Permiso denegado", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            plant_picture.setImageURI(data?.data)
            fileUri = data?.data!!
            filePath = getRealPathFromURI(fileUri)
        }
    }

    private fun getRealPathFromURI(contentURI: Uri): String? {
        val result: String?
        val cursor = contentResolver.query(contentURI, null, null, null, null)
        if (cursor == null) {
            result = contentURI.path
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            result = cursor.getString(idx)
            cursor.close()
        }
        return result
    }

    private fun postPlantSpecimen() {
        val file = File(filePath!!)
        val requestBody: RequestBody = create(MediaType.parse("image/*"), file)
        val image: MultipartBody.Part = MultipartBody.Part.createFormData("photo", file.name, requestBody)

        val date = Date()
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate: String = formatter.format(date)

        val currentUserRB: RequestBody = create(MediaType.parse("multipart/form-data"), currentUser.toString())
        val dateReceivedRB: RequestBody = create(MediaType.parse("multipart/form-data"), currentDate)
        val biostatusRB: RequestBody = create(MediaType.parse("multipart/form-data"), selectedBiostatus.toString())
        val specieRB: RequestBody = create(MediaType.parse("multipart/form-data"), selectedSpecie.toString())
        val completeRB: RequestBody = create(MediaType.parse("multipart/form-data"), "true")
        val statusRB: RequestBody = create(MediaType.parse("multipart/form-data"), selectedStatus.toString())
        val numberOfSamplesRB: RequestBody = create(MediaType.parse("multipart/form-data"), numberSpecimensInput.text.toString())
        val descriptionRB: RequestBody = create(MediaType.parse("multipart/form-data"), plantDescriptionInput.text.toString())
        val habitatRB: RequestBody = create(MediaType.parse("multipart/form-data"), selectedHabitat.toString())
        val habitatDescriptionRB: RequestBody = create(MediaType.parse("multipart/form-data"), selectedHabitatDescription.toString())
        val cityRB: RequestBody = create(MediaType.parse("multipart/form-data"), selectedCity.toString())
        val latitudeRB: RequestBody = create(MediaType.parse("multipart/form-data"), latitudeInput.text.toString())
        val longitudeRB: RequestBody = create(MediaType.parse("multipart/form-data"), longitudeInput.text.toString())
        val locationRB: RequestBody = create(MediaType.parse("multipart/form-data"), specificCollectionAreaInput.text.toString())

        if (layoutNewPlant.traveseAnyInput()) {
            Toast.makeText(applicationContext, getString(R.string.empty_fields_register), Toast.LENGTH_LONG).show()
        }
        else {
            newPlantViewModel.requestPostPlant(
                photo = image,
                user = currentUserRB,
                date_received = dateReceivedRB,
                biostatus = biostatusRB,
                species = specieRB,
                complete = completeRB,
                status = statusRB,
                number_of_samples = numberOfSamplesRB,
                description = descriptionRB,
                ecosystem = habitatRB,
                recolection_area_status = habitatDescriptionRB,
                city = cityRB,
                latitude = latitudeRB,
                longitude = longitudeRB,
                location = locationRB
            )
        }
    }
}