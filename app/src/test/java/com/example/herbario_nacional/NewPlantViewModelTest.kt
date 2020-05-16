package com.example.herbario_nacional

import android.os.Build
import com.example.herbario_nacional.repo.PlantRepository
import com.example.herbario_nacional.ui.viewModels.NewPlantViewModel
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.inject
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.koin.core.context.stopKoin
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@Config(sdk = [Build.VERSION_CODES.O_MR1])
@RunWith(RobolectricTestRunner::class)
class NewPlantViewModelTest: KoinTest {

    private val plantRepository: PlantRepository by inject()

    @Test
    fun newPlantViewmodelImpl_isCorrect(){
        val newPlantViewModel = NewPlantViewModel(plantRepository)
        val file: File? = File("./herbario_nacional/app/src/main/res/drawable-v24/placeholder.png")
        val requestBody: RequestBody = RequestBody.create(MediaType.parse("image/*"), file!!)
        val image: MultipartBody.Part = MultipartBody.Part.createFormData("photo", file.name, requestBody)

        val date = Date()
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("es"))
        val currentDate: String = formatter.format(date)

        val currentUserRB: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), "64")
        val dateReceivedRB: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), currentDate)
        val biostatusRB: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), "1")
        val specieRB: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), "1")
        val completeRB: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), "true")
        val statusRB: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), "1")
        val numberOfSamplesRB: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), "1")
        val descriptionRB: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), "1")
        val habitatRB: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), "1")
        val habitatDescriptionRB: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), "1")
        val cityRB: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), "1")
        val latitudeRB: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), "1")
        val longitudeRB: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), "1")
        val locationRB: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), "1")

        val dataReceived = runBlocking {
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
        assertNotNull(dataReceived)
    }

    @After fun stopKoinAfterTest() = stopKoin()
}