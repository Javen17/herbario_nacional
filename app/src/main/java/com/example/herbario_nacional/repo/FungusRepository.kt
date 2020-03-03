package com.example.herbario_nacional.repo

import com.example.herbario_nacional.data.network.`interface`.FungusInterface
import com.example.herbario_nacional.models.funghi.FunghiSpecimen

interface FungusRepository {
    suspend fun getFungus(): MutableList<FunghiSpecimen>
}

class FunghusRepositoryImpl(private  val fungusService: FungusInterface): FungusRepository{

    override suspend fun getFungus(): MutableList<FunghiSpecimen> {
        return fungusService.requestFungus()
    }


}