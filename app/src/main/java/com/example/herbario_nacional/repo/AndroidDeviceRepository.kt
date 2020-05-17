package com.example.herbario_nacional.repo

import com.example.herbario_nacional.data.network.`interface`.AndroidDeviceInterface
import com.example.herbario_nacional.models.AndroidDevice
import com.example.herbario_nacional.models.AndroidDeviceUpdate
import com.example.herbario_nacional.models.Status

interface AndroidDeviceRepository {
    suspend fun registerAndroidDevice(data: AndroidDevice): Status
    suspend fun updateAndroidDevice(id: Int, data: AndroidDeviceUpdate): Status
    suspend fun searchByAndroidDeviceId(value: String): MutableList<AndroidDevice>
}

class AndroidDeviceRepositoryImpl(private val androidDeviceService: AndroidDeviceInterface): AndroidDeviceRepository {
    override suspend fun registerAndroidDevice(data: AndroidDevice): Status {
        return androidDeviceService.requestPostAndroidDevice(data)
    }

    override suspend fun updateAndroidDevice(id: Int, data: AndroidDeviceUpdate): Status {
        return androidDeviceService.requestPutAndroidDevice(id, data)
    }

    override suspend fun searchByAndroidDeviceId(value: String): MutableList<AndroidDevice> {
        return androidDeviceService.searchAndroidDeviceId(value)
    }
}