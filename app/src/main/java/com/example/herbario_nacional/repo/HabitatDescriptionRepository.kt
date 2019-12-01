package com.example.herbario_nacional.repo

import com.example.herbario_nacional.data.network.`interface`.HabitatDescriptionInterface
import com.example.herbario_nacional.models.RecolectionAreaStatus

interface HabitatDescriptionRepository {
    suspend fun getHabitatDescription(): List<RecolectionAreaStatus>
}

class HabitatDescriptionRepositoryImpl(private val genusService: HabitatDescriptionInterface): HabitatDescriptionRepository{
    override suspend fun getHabitatDescription(): List<RecolectionAreaStatus> {
        return genusService.requestRecolectionAreaStatus()
    }
}