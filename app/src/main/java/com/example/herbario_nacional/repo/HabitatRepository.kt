package com.example.herbario_nacional.repo

import com.example.herbario_nacional.data.network.`interface`.HabitatInterface
import com.example.herbario_nacional.models.Ecosystem

interface HabitatRepository {
    suspend fun getHabitats(): List<Ecosystem>
}

class HabitatRepositoryImpl(private val habitatService: HabitatInterface): HabitatRepository{
    override suspend fun getHabitats(): List<Ecosystem> {
        return habitatService.requestEcosystem()
    }
}