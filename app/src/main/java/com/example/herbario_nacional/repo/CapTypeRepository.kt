package com.example.herbario_nacional.repo

import com.example.herbario_nacional.data.network.`interface`.CapTypeInterface
import com.example.herbario_nacional.models.funghi.CapType

interface CapTypeRepository {
    suspend fun getFormType(): List<CapType>
}

class CapTypeRepositoryImpl(private val capTypeService: CapTypeInterface): CapTypeRepository{
    override suspend fun getFormType(): List<CapType> {
        return capTypeService.requestCapType()
    }
}