package com.example.herbario_nacional.repo

import com.example.herbario_nacional.data.network.`interface`.GenusInterface
import com.example.herbario_nacional.models.Genus

interface GenusRepository {
    suspend fun getGenuses(): List<Genus>
}

class GenusRepositoryImpl(private val genusService: GenusInterface): GenusRepository{
    override suspend fun getGenuses(): List<Genus> {
        return genusService.requestGenus()
    }
}