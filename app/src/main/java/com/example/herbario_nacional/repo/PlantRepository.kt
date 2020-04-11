package com.example.herbario_nacional.repo

import com.example.herbario_nacional.data.network.`interface`.PlantInterface
import com.example.herbario_nacional.models.PlantSpecimen
import com.example.herbario_nacional.models.Status
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface PlantRepository {
    suspend fun getPlants(): MutableList<PlantSpecimen>
    suspend fun postPlant(
        photo: MultipartBody.Part,
        user: RequestBody,
        date_received: RequestBody,
        biostatus: RequestBody,
        species: RequestBody,
        complete: RequestBody,
        status: RequestBody,
        number_of_samples: RequestBody,
        description: RequestBody,
        ecosystem: RequestBody,
        recolection_area_status: RequestBody,
        city: RequestBody,
        latitude: RequestBody,
        longitude: RequestBody,
        location: RequestBody
    ): Status
}

class PlantRepositoryImpl(private val plantService: PlantInterface): PlantRepository{
    override suspend fun getPlants(): MutableList<PlantSpecimen> {
        return plantService.requestPlant()
    }

    override suspend fun postPlant(
        photo: MultipartBody.Part,
        user: RequestBody,
        date_received: RequestBody,
        biostatus: RequestBody,
        species: RequestBody,
        complete: RequestBody,
        status: RequestBody,
        number_of_samples: RequestBody,
        description: RequestBody,
        ecosystem: RequestBody,
        recolection_area_status: RequestBody,
        city: RequestBody,
        latitude: RequestBody,
        longitude: RequestBody,
        location: RequestBody
    ): Status {
        return plantService.requestPostPlant(
            photo,
            user,
            date_received,
            biostatus,
            species,
            complete,
            status,
            number_of_samples,
            description,
            ecosystem,
            recolection_area_status,
            city,
            latitude,
            longitude,
            location
        )
    }
}