package com.example.herbario_nacional.repo

import com.example.herbario_nacional.data.network.`interface`.MeInterface
import com.example.herbario_nacional.models.Me
import com.example.herbario_nacional.models.MeData
import com.example.herbario_nacional.models.Status
import okhttp3.RequestBody

interface MeRepository {
    suspend fun getMe(): Me
        suspend fun updateMe(data: RequestBody): Status
}

class MeRepositoryImpl(private val meService: MeInterface): MeRepository{
    override suspend fun getMe(): Me {
        return meService.requestMe()
    }

    override suspend fun updateMe(data: RequestBody): Status{
        return meService.updateAccount(data);
    }


}