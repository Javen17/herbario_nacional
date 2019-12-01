package com.example.herbario_nacional.repo

import com.example.herbario_nacional.data.network.`interface`.PlantInterface
import com.example.herbario_nacional.models.PlantSpecimen

interface PlantRepository {
    suspend fun getPlants(): MutableList<PlantSpecimen>
}

class PlantRepositoryImpl(private val plantService: PlantInterface): PlantRepository{
    override suspend fun getPlants(): MutableList<PlantSpecimen> {
        return plantService.requestPlant()
    }
}