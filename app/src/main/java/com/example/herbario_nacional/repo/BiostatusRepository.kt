package com.example.herbario_nacional.repo

import com.example.herbario_nacional.data.network.`interface`.BiostatusInterface
import com.example.herbario_nacional.models.Biostatus

interface BiostatusRepository {
    suspend fun getBiostatus(): List<Biostatus>
}

class BiostatusRepositoryImpl(private val biostatusService: BiostatusInterface): BiostatusRepository{
    override suspend fun getBiostatus(): List<Biostatus> {
        return biostatusService.requestBiostatus()
    }
}