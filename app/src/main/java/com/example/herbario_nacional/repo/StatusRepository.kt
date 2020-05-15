package com.example.herbario_nacional.repo

import com.example.herbario_nacional.data.network.`interface`.StatusInterface
import com.example.herbario_nacional.models.PlantStatus

interface StatusRepository {
    suspend fun getStatus(): List<PlantStatus>
}

class StatusRepositoryImpl(private val statusService: StatusInterface): StatusRepository{
    override suspend fun getStatus(): List<PlantStatus> {
        return statusService.requestStatus()
    }
}