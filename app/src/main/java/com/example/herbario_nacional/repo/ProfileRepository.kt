package com.example.herbario_nacional.repo

import com.example.herbario_nacional.data.network.`interface`.ProfileInterface
import com.example.herbario_nacional.models.Profile
import com.example.herbario_nacional.models.Status

interface ProfileRepository {
    suspend fun registerProfile(data: Profile): Status
}

class ProfileRepositoryImpl(private val profileService: ProfileInterface): ProfileRepository {
    override suspend fun registerProfile(data: Profile): Status {
        return profileService.requestProfile(data)
    }
}
