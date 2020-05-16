package com.example.herbario_nacional

import android.os.Build
import com.example.herbario_nacional.data.network.`interface`.PlantInterface
import com.example.herbario_nacional.repo.PlantRepository
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.O_MR1])
@RunWith(RobolectricTestRunner::class)
class PlantRepositoryTest: KoinTest {
    private val plantService: PlantInterface by inject()

    @Test
    fun plantRepositoryImpl_isCorrect(){
        val dataReceived = runBlocking {plantService.requestPlant()}
        assertNotNull(dataReceived)
    }

    @After
    fun stopKoinAfterTest() = stopKoin()
}