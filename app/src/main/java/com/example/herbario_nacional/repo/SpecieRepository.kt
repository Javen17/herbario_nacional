package com.example.herbario_nacional.repo

import com.example.herbario_nacional.data.network.`interface`.SpecieInterface
import com.example.herbario_nacional.models.PlantSpecies

interface SpecieRepository {
    suspend fun getSpecies(): List<PlantSpecies>
}

class SpecieRepositoryImpl(private val specieService: SpecieInterface): SpecieRepository {
    override suspend fun getSpecies(): List<PlantSpecies> {
        return specieService.requestSpecie()
    }
}