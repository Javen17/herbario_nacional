package com.example.herbario_nacional.repo

import com.example.herbario_nacional.data.network.`interface`.CountryInterface
import com.example.herbario_nacional.models.Request

interface CountryRepository {
    suspend fun getCountries(): Request
}

class CountryRepositoryImpl(private val countryService: CountryInterface): CountryRepository{
    override suspend fun getCountries(): Request {
        return countryService.requestCountry()
    }
}