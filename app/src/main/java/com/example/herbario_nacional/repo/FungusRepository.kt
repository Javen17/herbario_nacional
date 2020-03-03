package com.example.herbario_nacional.repo

import com.example.herbario_nacional.data.network.`interface`.FungusInterface
import com.example.herbario_nacional.models.funghi.PostFungusSpecimen
import com.example.herbario_nacional.models.Status
import com.example.herbario_nacional.models.funghi.FunghiSpecimen

interface FungusRepository {
    suspend fun getFungus(): MutableList<FunghiSpecimen>
    suspend fun postFungus(data: PostFungusSpecimen): Status
}

class FunghusRepositoryImpl(private  val fungusService: FungusInterface): FungusRepository{

    override suspend fun getFungus(): MutableList<FunghiSpecimen> {
        return fungusService.requestFungus()
    }

    override suspend fun postFungus(data: PostFungusSpecimen): Status {
        return fungusService.requestPostFungus(data)
    }

}