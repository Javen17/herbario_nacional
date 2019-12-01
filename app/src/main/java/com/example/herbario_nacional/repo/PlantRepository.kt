package com.example.herbario_nacional.repo

import com.example.herbario_nacional.data.network.`interface`.PlantInterface
import com.example.herbario_nacional.models.PlantSpecimen
import com.example.herbario_nacional.models.PostPlantSpecimen
import com.example.herbario_nacional.models.Status
import com.example.herbario_nacional.util.StatusCode

interface PlantRepository {
    suspend fun getPlants(): MutableList<PlantSpecimen>
    suspend fun postPlant(data: PostPlantSpecimen): Status
}

class PlantRepositoryImpl(private val plantService: PlantInterface): PlantRepository{
    override suspend fun getPlants(): MutableList<PlantSpecimen> {
        return plantService.requestPlant()
    }

    override suspend fun postPlant(data: PostPlantSpecimen): Status {
        return plantService.requestPostPlant(data)
    }
}