package com.example.herbario_nacional.repo

import com.example.herbario_nacional.data.network.`interface`.MeInterface
import com.example.herbario_nacional.models.Me

interface MeRepository {
    suspend fun getMe(): Me
}

class MeRepositoryImpl(private val meService: MeInterface): MeRepository{
    override suspend fun getMe(): Me {
        return meService.requestMe()
    }
}