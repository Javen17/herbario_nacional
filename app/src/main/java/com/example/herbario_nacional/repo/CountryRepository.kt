package com.example.herbario_nacional.repo

import com.example.herbario_nacional.data.network.`interface`.CountryInterface
import com.example.herbario_nacional.models.countries.Country

interface CountryRepository {
    suspend fun getCountries(): List<Country>
}

class CountryRepositoryImpl(private val countryService: CountryInterface): CountryRepository{
    override suspend fun getCountries(): List<Country> {
        return countryService.requestCountry()
    }
}