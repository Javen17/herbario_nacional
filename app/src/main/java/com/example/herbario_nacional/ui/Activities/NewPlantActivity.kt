package com.example.herbario_nacional.ui.Activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import com.chivorn.smartmaterialspinner.SmartMaterialSpinner
import com.example.herbario_nacional.R
import com.example.herbario_nacional.ui.viewModels.*
import com.example.herbario_nacional.util.traveseAnyInput
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.common.collect.ArrayListMultimap
import com.google.common.collect.Multimap
import kotlinx.android.synthetic.main.activity_new_plant.*
import kotlinx.android.synthetic.main.new_plant.*
import kotlinx.android.synthetic.main.plant_content.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.create
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

class NewPlantActivity : AppCompatActivity() {
    private val meViewModel: MeViewModel by viewModel()
    private val countryViewModel: CountryViewModel by viewModel()
    private val stateViewModel: StateViewModel by viewModel()
    private val cityViewModel: CityViewModel by viewModel()
    private val specieViewModel: SpecieViewModel by viewModel()
    private val statusViewModel: StatusViewModel by viewModel()
    private val habitatViewModel: HabitatViewModel by viewModel()
    private val habitatDescriptionViewModel: HabitatDescriptionViewModel by viewModel()
    private val biostatusViewModel: BiostatusViewModel by viewModel()
    private val newPlantViewModel: NewPlantViewModel by viewModel()

    var currentUser: Int = 0

    val species: MutableList<String> = ArrayList()
    val specieMap: MutableMap<Int, String> = mutableMapOf()
    var selectedSpecie: Int = 0

    val status: MutableList<String> = ArrayList()
    val statusMap: MutableMap<Int, String> = mutableMapOf()
    var selectedStatus: Int = 0

    val countries: MutableList<String> = ArrayList()
    val countryMap: MutableMap<Int, String> = mutableMapOf()

    private val stateMultiMap: Multimap<Int, String> = ArrayListMultimap.create()
    private val tempState: MutableList<String> = ArrayList()

    private val cityMultiMap: Multimap<String, String> = ArrayListMultimap.create()
    val cityMap: MutableMap<Int, String> = mutableMapOf()
    private val tempCity: MutableList<String> = ArrayList()
    var currentCity: String = ""
    var selectedCity: Int = 0

    var selectedCountry: Int by Delegates.observable(0) { _, _, newValue ->
        tempState.clear()
        tempCity.clear()

        stateMultiMap.entries().forEach {
            if (it.key == newValue) tempState.add(it.value)
        }

        val stateSpinner: SmartMaterialSpinner<String> = findViewById(R.id.stateSpinner)
        stateSpinner.item = tempState

        stateSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedState = tempState[position]
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                print("Estado no seleccionado")
            }
        }
        val citySpinner: SmartMaterialSpinner<String> = findViewById(R.id.citySpinner)
        citySpinner.item = tempCity
    }

    var selectedState: String by Delegates.observable("") { _, _, newValue ->
        tempCity.clear()

        val citySpinner: SmartMaterialSpinner<String> = findViewById(R.id.citySpinner)
        citySpinner.item = tempCity

        cityMultiMap.entries().forEach {
            if (it.key == newValue) tempCity.add(it.value)
        }

        citySpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                currentCity = tempCity[position]

                for((cityId, value) in cityMap) {
                    if (currentCity == value) selectedCity = cityId
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                print("Ciudad no seleccionada")
            }
        }
    }

    val habitats: MutableList<String> = ArrayList()
    val habitatMap: MutableMap<Int, String> = mutableMapOf()
    var selectedHabitat: Int = 0

    val habitatDescription: MutableList<String> = ArrayList()
    val habitatDescriptionMap: MutableMap<Int, String> = mutableMapOf()
    var selectedHabitatDescription: Int = 0

    val biostatus: MutableList<String> = ArrayList()
    val biostatusMap: MutableMap<Int, String> = mutableMapOf()
    var selectedBiostatus: Int = 0

    private lateinit var currentPhotoPath: String
    private lateinit var file: File
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
                        if (it.genus.family.type == "planta") {
                            specieMap[it.id] = it.scientific_name
                            species.add(it.scientific_name)
                        }
                    }
                }
                val specieSpinner: SmartMaterialSpinner<String> = findViewById(R.id.specieSpinner)
                specieSpinner.item = species

                specieSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        for ((specieId, value) in specieMap) {
                            if (value == species[position]) selectedSpecie = specieId
                        }
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        print("Especie no seleccionada")
                    }
                }
            }
            if (dataState.error != null && !dataState.error.consumed){
                dataState.error.consume()?.let { error ->
                    Toast.makeText(applicationContext, resources.getString(error), Toast.LENGTH_LONG).show()
                }
            }
        })

        statusViewModel.uiState.observe(this, Observer {
            val dataState = it ?: return@Observer
            if (dataState.result != null && !dataState.result.consumed){
                dataState.result.consume()?.let { result ->
                    result.forEach{
                        statusMap[it.id] = it.name
                        status.add(it.name)
                    }
                }
                val plantStatusSpinner: SmartMaterialSpinner<String> = findViewById(R.id.plantStatusSpinner)
                plantStatusSpinner.item = status

                plantStatusSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        for ((statusId, value) in statusMap) {
                            if (value == status[position]) selectedStatus = statusId
                        }
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        print("Estado de planta no seleccionado")
                    }
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
                    result.forEach{
                        countryMap[it.id] = it.name
                        countries.add(it.name)
                    }
                }

                val countrySpinner: SmartMaterialSpinner<String> = findViewById(R.id.countrySpinner)
                countrySpinner.item = countries

                countrySpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        for ((countryId, value) in countryMap) {
                            if (value == countries[position]) selectedCountry = countryId
                        }
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        Toast.makeText(applicationContext, "País no seleccionada", Toast.LENGTH_LONG).show()
                    }
                }
            }
            if (dataState.error != null && !dataState.error.consumed){
                dataState.error.consume()?.let { error ->
                    Toast.makeText(applicationContext, resources.getString(error), Toast.LENGTH_LONG).show()
                }
            }
        })

        stateViewModel.uiState.observe(this, Observer {
            val dataState = it ?: return@Observer
            if (dataState.result != null && !dataState.result.consumed){
                dataState.result.consume()?.let { result ->
                    result.forEach{
                        stateMultiMap.put(it.country.id, it.name)
                    }
                }
            }
            if (dataState.error != null && !dataState.error.consumed){
                dataState.error.consume()?.let { error ->
                    Toast.makeText(applicationContext, resources.getString(error), Toast.LENGTH_LONG).show()
                }
            }
        })

        cityViewModel.uiState.observe(this, Observer {
            val dataState = it ?: return@Observer
            if (dataState.result != null && !dataState.result.consumed){
                dataState.result.consume()?.let { result ->
                    result.forEach{
                        cityMultiMap.put(it.state.name, it.name)
                        cityMap[it.id] = it.name
                    }
                }
            }
            if (dataState.error != null && !dataState.error.consumed){
                dataState.error.consume()?.let { error ->
                    Toast.makeText(applicationContext, resources.getString(error), Toast.LENGTH_LONG).show()
                }
            }
        })

        habitatViewModel.uiState.observe(this, Observer {
            val dataState = it ?: return@Observer
            if (dataState.result != null && !dataState.result.consumed){
                dataState.result.consume()?.let { result ->
                    result.forEach{
                        habitatMap[it.id] = it.name
                        habitats.add(it.name)
                    }
                }
                val habitatSpinner: SmartMaterialSpinner<String> = findViewById(R.id.habitatSpinner)
                habitatSpinner.item = habitats

                habitatSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        for ((habitatId, value) in habitatMap) {
                            if (value == habitats[position]) selectedHabitat = habitatId
                        }
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        print("Hábitat no seleccionada")
                    }
                }
            }
            if (dataState.error != null && !dataState.error.consumed){
                dataState.error.consume()?.let { error ->
                    Toast.makeText(applicationContext, resources.getString(error), Toast.LENGTH_LONG).show()
                }
            }
        })

        habitatDescriptionViewModel.uiState.observe(this, Observer {
            val dataState = it ?: return@Observer
            if (dataState.result != null && !dataState.result.consumed){
                dataState.result.consume()?.let { result ->
                    result.forEach{
                        habitatDescriptionMap[it.id] = it.name
                        habitatDescription.add(it.name)
                    }
                }
                val habitatDescriptionSpinner: SmartMaterialSpinner<String> = findViewById(R.id.habitatDescriptionSpinner)
                habitatDescriptionSpinner.item = habitatDescription

                habitatDescriptionSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        for ((habitatDescriptionId, value) in habitatDescriptionMap) {
                            if (value == habitatDescription[position]) selectedHabitatDescription = habitatDescriptionId
                        }
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        print("Descripción de hábitat no seleccionada")
                    }
                }
            }
            if (dataState.error != null && !dataState.error.consumed){
                dataState.error.consume()?.let { error ->
                    Toast.makeText(applicationContext, resources.getString(error), Toast.LENGTH_LONG).show()
                }
            }
        })

        biostatusViewModel.uiState.observe(this, Observer {
            val dataState = it ?: return@Observer
            if (dataState.result != null && !dataState.result.consumed){
                dataState.result.consume()?.let { result ->
                    result.forEach{
                        biostatusMap[it.id] = it.name
                        biostatus.add(it.name)
                    }
                }
                val biostatusSpinner: SmartMaterialSpinner<String> = findViewById(R.id.biostatusSpinner)
                biostatusSpinner.item = biostatus

                biostatusSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        for ((biostatusId, value) in biostatusMap) {
                            if (value == biostatus[position]) selectedBiostatus = biostatusId
                        }
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        print("Bioestado no seleccionado")
                    }
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
            postPlantSpecimen()
        }

        newPlantViewModel.uiState.observe(this, Observer {
            loading.visibility = View.VISIBLE
            register_btn.visibility = View.GONE
            cancel_btn.visibility = View.GONE

            val dataState = it ?: return@Observer
            if (dataState.status != null && !dataState.status.consumed){
                dataState.status.consume()?.let { status ->
                    loading.visibility = View.VISIBLE
                    register_btn.visibility = View.GONE
                    cancel_btn.visibility = View.GONE
                    if(status.result == "") {
                        Toast.makeText(applicationContext, getString(R.string.sucess), Toast.LENGTH_LONG).show()
                        showActivity(MainActivity::class.java)
                    }
                }
            }

            if (dataState.error != null && !dataState.error.consumed){
                dataState.error.consume()?.let { error ->
                    loading.visibility = View.GONE
                    register_btn.visibility = View.VISIBLE
                    cancel_btn.visibility = View.VISIBLE
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

    companion object {
        private const val REQUEST_TAKE_PHOTO = 1
        private const val IMAGE_PICK_CODE = 1000
        private const val PERMISSION_CODE = 1001
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale("es")).format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    private fun galleryAddPic() {
        Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).also {
            file = File(currentPhotoPath)
            it.data = Uri.fromFile(file)
            sendBroadcast(it)
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    Toast.makeText(this, "Ha ocurrido un error.", Toast.LENGTH_SHORT).show()
                    null
                }

                photoFile?.also {
                    val photoUri: Uri = FileProvider.getUriForFile(
                        this,
                        "com.example.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
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
            file = File(filePath!!)
        }

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_TAKE_PHOTO) {
            galleryAddPic()
            val photo: Bitmap = BitmapFactory.decodeFile(currentPhotoPath)
            plant_picture.setImageBitmap(photo)
            Timber.e("Uri: ${currentPhotoPath.toUri()}")
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
        val requestBody: RequestBody = create(MediaType.parse("image/*"), file)
        val image: MultipartBody.Part = MultipartBody.Part.createFormData("photo", file.name, requestBody)

        val date = Date()
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale("es"))
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
            Toast.makeText(applicationContext, getString(R.string.empty_fields_data_sheet), Toast.LENGTH_LONG).show()
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

    private fun showActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        this.finish()
    }
}