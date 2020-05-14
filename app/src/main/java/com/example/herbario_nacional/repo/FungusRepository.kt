package com.example.herbario_nacional.repo

import com.example.herbario_nacional.data.network.`interface`.FungusInterface
import com.example.herbario_nacional.models.funghi.PostFungusSpecimen
import com.example.herbario_nacional.models.Status
import com.example.herbario_nacional.models.funghi.FunghiSpecimen
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface FungusRepository {
    suspend fun getFungus(): MutableList<FunghiSpecimen>
    suspend fun postFungus(
        photo: MultipartBody.Part,
        user: RequestBody,
        date_received: RequestBody,
        number_of_samples: RequestBody,
        description: RequestBody,
        cap: RequestBody,
        forms: RequestBody,
        crust: RequestBody,
        color: RequestBody,
        change_of_color: RequestBody,
        species: RequestBody,
        smell: RequestBody,
        status: RequestBody,
        city: RequestBody,
        ecosystem: RequestBody,
        recolection_area_status: RequestBody,
        latitude: RequestBody,
        longitude: RequestBody,
        aditional_info: RequestBody,
        location: RequestBody
    ): Status
    suspend fun searchByName(value: String): MutableList<FunghiSpecimen>
    suspend fun searchByLocation(value: String): MutableList<FunghiSpecimen>
    suspend fun searchByRecollectionArea(value: String): MutableList<FunghiSpecimen>
}

class FunghusRepositoryImpl(private  val fungusService: FungusInterface): FungusRepository{

    override suspend fun getFungus(): MutableList<FunghiSpecimen> {
        return fungusService.requestFungus()
    }

    override suspend fun postFungus(
        photo: MultipartBody.Part,
        user: RequestBody,
        date_received: RequestBody,
        number_of_samples: RequestBody,
        description: RequestBody,
        cap: RequestBody,
        forms: RequestBody,
        crust: RequestBody,
        color: RequestBody,
        change_of_color: RequestBody,
        species: RequestBody,
        smell: RequestBody,
        status: RequestBody,
        city: RequestBody,
        ecosystem: RequestBody,
        recolection_area_status: RequestBody,
        latitude: RequestBody,
        longitude: RequestBody,
        aditional_info: RequestBody,
        location: RequestBody
    ): Status {
        return fungusService.requestPostFungus(
            photo,
            user,
            date_received,
            number_of_samples,
            description,
            cap,
            forms,
            crust,
            color,
            change_of_color,
            species,
            smell,
            status,
            city,
            ecosystem,
            recolection_area_status,
            latitude,
            longitude,
            aditional_info,
            location
        )
    }

    override suspend fun searchByName(value: String): MutableList<FunghiSpecimen> {
        return fungusService.searchFungusByName(value)
    }

    override suspend fun searchByLocation(value: String): MutableList<FunghiSpecimen> {
        return fungusService.searchFungusByLocation(value)
    }

    override suspend fun searchByRecollectionArea(value: String): MutableList<FunghiSpecimen> {
        return fungusService.searchFungusByRecollectionArea(value)
    }
}