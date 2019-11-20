package com.example.herbario_nacional.repo

import com.example.herbario_nacional.data.network.`interface`.RegisterInterface
import com.example.herbario_nacional.models.Register
import com.example.herbario_nacional.models.Status

interface RegisterRepository {
    suspend fun registerUser(data: Register): Status
}

class RegisterRepositoryImpl(private val registerService: RegisterInterface): RegisterRepository {
    override suspend fun registerUser(data: Register): Status {
        return registerService.requestRegister(data)
    }
}
