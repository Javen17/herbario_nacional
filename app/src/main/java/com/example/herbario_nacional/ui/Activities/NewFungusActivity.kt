package com.example.herbario_nacional.ui.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import com.example.herbario_nacional.R
import com.example.herbario_nacional.models.funghi.PostFungusSpecimen
import com.example.herbario_nacional.ui.viewModels.*
import com.example.herbario_nacional.util.traveseAnyInput
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.isapanah.awesomespinner.AwesomeSpinner
import kotlinx.android.synthetic.main.activity_new_plant.*
import kotlinx.android.synthetic.main.new_fungus.*
import kotlinx.android.synthetic.main.new_plant.*
import kotlinx.android.synthetic.main.new_plant.cancel_btn
import kotlinx.android.synthetic.main.new_plant.register_btn
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NewFungusActivity : AppCompatActivity() {
    private val meViewModel: MeViewModel by viewModel()
    private val countryViewModel: CountryViewModel by viewModel()
    private val stateViewModel: StateViewModel by viewModel()
    private val cityViewModel: CityViewModel by viewModel()
    private val familyViewModel: FamilyViewModel by viewModel()
    private val genusViewModel: GenusViewModel by viewModel()
    private val specieViewModel: SpecieViewModel by viewModel()
    private val capTypeViewModel: CapTypeViewModel by viewModel()
    private val formTypeViewModel: FormTypeViewModel by viewModel()
    private val statusViewModel: StatusViewModel by viewModel()
    private val habitatViewModel: HabitatViewModel by viewModel()
    private val habitatDescriptionViewModel: HabitatDescriptionViewModel by viewModel()
    private val biostatusViewModel: BiostatusViewModel by viewModel()
    private val newFungusViewModel: NewFungusViewModel by viewModel()


    var currentUser: Int = 0

    val families: ArrayList<String> = ArrayList()
    val familyMap: MutableMap<Int, String> = mutableMapOf()
    var selectedFamily: Int = 0

    val genuses: ArrayList<String> = ArrayList()
    val genusMap: MutableMap<Int, String> = mutableMapOf()
    var selectedGenus: Int = 0

    val species: ArrayList<String> = ArrayList()
    val specieMap: MutableMap<Int, String> = mutableMapOf()
    var selectedSpecie: Int = 0

    val capType: ArrayList<String> = ArrayList()
    val capTypeMap: MutableMap<Int, String> = mutableMapOf()
    var selectedCapType: Int = 0

    val formType: ArrayList<String> = ArrayList()
    val formTypeMap: MutableMap<Int, String> = mutableMapOf()
    var selectedFormType: Int = 0

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_fungus)

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

        familyViewModel.uiState.observe(this, Observer {
            val dataState = it ?: return@Observer
            if (dataState.result != null && !dataState.result.consumed){
                dataState.result.consume()?.let { result ->
                    result.forEach{
                        if (it.type == "hongo") {
                            familyMap[it.id] = it.name
                            families.add(it.name)
                        }
                    }
                }
            }
            if (dataState.error != null && !dataState.error.consumed){
                dataState.error.consume()?.let { error ->
                    Toast.makeText(applicationContext, resources.getString(error), Toast.LENGTH_LONG).show()
                }
            }
        })

        val familySpinner = findViewById<View>(R.id.familySpinner) as AwesomeSpinner
        val familiesAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, families)
        familySpinner.setAdapter(familiesAdapter)

        familySpinner.setOnSpinnerItemClickListener { _, itemAtPosition ->
            for ((id, value) in familyMap) {
                if (value == itemAtPosition) selectedFamily = id
            }
        }

        genusViewModel.uiState.observe(this, Observer {
            val dataState = it ?: return@Observer
            if (dataState.result != null && !dataState.result.consumed){
                dataState.result.consume()?.let { result ->
                    result.forEach{
                        genusMap[it.id] = it.name
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

        val genusSpinner = findViewById<View>(R.id.genusSpinner) as AwesomeSpinner
        val genusesAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genuses)
        genusSpinner.setAdapter(genusesAdapter)

        genusSpinner.setOnSpinnerItemClickListener { _, itemAtPosition ->
            for ((id, value) in genusMap) {
                if (value == itemAtPosition) selectedGenus = id
            }
        }

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

        capTypeViewModel.uiState.observe(this, Observer {
            val dataState = it ?: return@Observer
            if (dataState.result != null && !dataState.result.consumed){
                dataState.result.consume()?.let { result ->
                    result.forEach{
                        capTypeMap[it.id] = it.name
                        capType.add(it.name)
                    }
                }
            }
            if (dataState.error != null && !dataState.error.consumed){
                dataState.error.consume()?.let { error ->
                    Toast.makeText(applicationContext, resources.getString(error), Toast.LENGTH_LONG).show()
                }
            }
        })

        val capTypeSpinner = findViewById<View>(R.id.capTypeSpinner) as AwesomeSpinner
        val capTypeAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, capType)
        capTypeSpinner.setAdapter(capTypeAdapter)

        capTypeSpinner.setOnSpinnerItemClickListener { _, itemAtPosition ->
            for ((id, value) in capTypeMap) {
                if (value == itemAtPosition) selectedCapType = id
            }
        }

        formTypeViewModel.uiState.observe(this, Observer {
            val dataState = it ?: return@Observer
            if (dataState.result != null && !dataState.result.consumed){
                dataState.result.consume()?.let { result ->
                    result.forEach{
                        formTypeMap[it.id] = it.name
                        formType.add(it.name)
                    }
                }
            }
            if (dataState.error != null && !dataState.error.consumed){
                dataState.error.consume()?.let { error ->
                    Toast.makeText(applicationContext, resources.getString(error), Toast.LENGTH_LONG).show()
                }
            }
        })

        val formTypeSpinner = findViewById<View>(R.id.formTypeSpinner) as AwesomeSpinner
        val formTypeAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, formType)
        formTypeSpinner.setAdapter(formTypeAdapter)

        formTypeSpinner.setOnSpinnerItemClickListener { _, itemAtPosition ->
            for ((id, value) in formTypeMap) {
                if (value == itemAtPosition) selectedFormType = id
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

        val statusSpinner = findViewById<View>(R.id.fungusStatusSpinner) as AwesomeSpinner
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

        register_btn.setOnClickListener{
            val date = Date()
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val currentDate: String = formatter.format(date)

            if (layoutNewFungus.traveseAnyInput()) {
                Toast.makeText(applicationContext, getString(R.string.empty_fields_register), Toast.LENGTH_LONG).show()
            }
            else {
                newFungusViewModel.requestPostFungus(
                    PostFungusSpecimen(
                        user = currentUser,
                        photo = null,
                        date_received = currentDate,
                        biostatus = selectedBiostatus,
                        // family = selectedFamily,
                        // genus = selectedGenus,
                        species = selectedSpecie,
                        crust = getCheckedRatio(),
                        cap = selectedCapType,
                        forms = selectedFormType,
                        color = colorInput.text.toString(),
                        change_of_color = changeOfColorInput.text.toString(),
                        smell = smellInput.text.toString(),
                        additionalInfo = "",
                        complete = true,
                        status = selectedStatus,
                        number_of_samples = numberSpecimensFInput.text.toString().toInt(),
                        description = fungusDescriptionInput.text.toString(),
                        ecosystem = selectedHabitat,
                        recolection_area_status = selectedHabitatDescription,
                        // country = selectedCountry,
                        // state = selectedState,
                        city = selectedCity,
                        latitude = latitudeFInput.text.toString().toDouble(),
                        longitude = longitudeFInput.text.toString().toDouble(),
                        location = specificCollectionAreaFInput.text.toString()
                    )
                )
            }
        }

        newFungusViewModel.uiState.observe(this, Observer {
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

        val newFungus: ConstraintLayout = findViewById(R.id.new_fungus)
        val bottomSheetBehavior = BottomSheetBehavior.from(newFungus)

        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun getCheckedRatio(): Boolean {
        val crustRG: RadioGroup = findViewById(R.id.crustRadioGroup)
        val selectedId = crustRG.checkedRadioButtonId
        val selectedRB: RadioButton = findViewById(selectedId)

        return selectedRB.text.toString() != "No"
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
