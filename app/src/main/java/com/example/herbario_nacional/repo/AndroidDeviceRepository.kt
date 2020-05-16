package com.example.herbario_nacional.repo

import com.example.herbario_nacional.data.network.`interface`.AndroidDeviceInterface
import com.example.herbario_nacional.models.AndroidDevice
import com.example.herbario_nacional.models.Status

interface AndroidDeviceRepository {
    suspend fun registerAndroidDevice(data: AndroidDevice): Status
}

class AndroidDeviceRepositoryImpl(private val androidDeviceService: AndroidDeviceInterface): AndroidDeviceRepository {
    override suspend fun registerAndroidDevice(data: AndroidDevice): Status {
        return androidDeviceService.requestPostAndroidDevice(data)
    }
}