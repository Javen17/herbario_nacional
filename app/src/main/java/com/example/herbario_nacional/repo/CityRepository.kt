package com.example.herbario_nacional.repo

import com.example.herbario_nacional.data.network.`interface`.CityInterface
import com.example.herbario_nacional.models.location.City

interface CityRepository {
    suspend fun getCities(): List<City>
}

class CityRepositoryImpl(private val cityService: CityInterface): CityRepository{
    override suspend fun getCities(): List<City> {
        return cityService.requestCity()
    }
}