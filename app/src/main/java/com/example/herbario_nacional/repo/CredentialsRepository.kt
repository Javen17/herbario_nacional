package com.example.herbario_nacional.repo

import android.util.Log
import com.example.herbario_nacional.data.network.`interface`.CredentialsInterface
import com.example.herbario_nacional.models.Credentials
import com.example.herbario_nacional.models.EmailData
import com.example.herbario_nacional.models.Message
import com.example.herbario_nacional.models.Status

interface CredentialsRepository {
    suspend fun getLogin(credentials: Credentials): Status
    suspend fun getPermanentLogin(): Message

    suspend fun resetPassword(email: EmailData): Status
}

class CredentialsRepositoryImpl(private val credentialService: CredentialsInterface): CredentialsRepository{
    override suspend fun getLogin(credentials: Credentials): Status {
        return credentialService.requestLogin(credentials)
    }

    override suspend fun getPermanentLogin(): Message {
        return credentialService.requestPermanetLogin()
    }

    override suspend fun resetPassword(email: EmailData): Status{
        Log.i("RESET PASSWORD", "REPOSITORY FUN REACHED");
        return credentialService.resetPassword(email);
    }
}