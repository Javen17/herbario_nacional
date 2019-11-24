package com.example.herbario_nacional.repo

import com.example.herbario_nacional.data.network.`interface`.CountryInterface
import com.example.herbario_nacional.models.Message

interface CountryRepository {
    suspend fun getCountries(): Message
}

class CountryRepositoryImpl(private val countryService: CountryInterface): CountryRepository{
    override suspend fun getCountries(): Message {
        return countryService.requestCountry()
    }
}