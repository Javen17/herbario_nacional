package com.example.herbario_nacional

import android.os.Build
import com.example.herbario_nacional.data.network.`interface`.FungusInterface
import junit.framework.Assert
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
class FungusRepositoryTest: KoinTest {
    private val fungusService: FungusInterface by inject()

    @Test
    fun fungusRepositoryImpl_isCorrect(){
        val dataReceived = runBlocking {fungusService.requestFungus()}
        Assert.assertNotNull(dataReceived)
    }

    @After
    fun stopKoinAfterTest() = stopKoin()
}