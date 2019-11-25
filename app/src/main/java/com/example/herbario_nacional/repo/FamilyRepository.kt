package com.example.herbario_nacional.repo

import com.example.herbario_nacional.data.network.`interface`.FamilyInterface
import com.example.herbario_nacional.models.PlantFamily

interface FamilyRepository {
    suspend fun getFamilies(): List<PlantFamily>
}

class FamilyRepositoryImpl(private val familyService: FamilyInterface): FamilyRepository{
    override suspend fun getFamilies(): List<PlantFamily> {
        return familyService.requestFamily()
    }
}