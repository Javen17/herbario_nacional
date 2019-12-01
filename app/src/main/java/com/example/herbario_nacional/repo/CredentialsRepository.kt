package com.example.herbario_nacional.repo

import com.example.herbario_nacional.data.network.`interface`.CredentialsInterface
import com.example.herbario_nacional.models.Credentials
import com.example.herbario_nacional.models.Message
import com.example.herbario_nacional.models.Status

interface CredentialsRepository {
    suspend fun getLogin(credentials: Credentials): Status
    suspend fun getPermanentLogin(): Message
}

class CredentialsRepositoryImpl(private val credentialService: CredentialsInterface): CredentialsRepository{
    override suspend fun getLogin(credentials: Credentials): Status {
        return credentialService.requestLogin(credentials)
    }

    override suspend fun getPermanentLogin(): Message {
        return credentialService.requestPermanetLogin()
    }
}