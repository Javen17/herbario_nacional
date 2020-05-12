package com.example.herbario_nacional

import android.os.Build
import com.example.herbario_nacional.data.network.`interface`.CredentialsInterface
import com.example.herbario_nacional.models.Credentials
import com.example.herbario_nacional.repo.CredentialsRepository
import com.example.herbario_nacional.ui.viewModels.CredentialsViewModel
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.inject
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.koin.core.context.stopKoin


@Config(sdk = [Build.VERSION_CODES.O_MR1])
@RunWith(RobolectricTestRunner::class)
class CredentialsViewModelTest: KoinTest {

    private val credentialsRepository: CredentialsRepository by inject()

    @Test
    fun loginViewmodelImpl_isCorrect(){
        val credentialsViewModel = CredentialsViewModel(credentialsRepository)
        val expected = Credentials("admin2", "admin2")
        val anotherUser = Credentials("admin", "admin")

        val dataReceived = runBlocking { credentialsViewModel.requestLogin(expected.username, expected.password) }
        val dataReceived2 = runBlocking { credentialsViewModel.requestLogin(anotherUser.username, anotherUser.password) }

        assertNotNull(dataReceived)
        assertEquals(dataReceived, dataReceived2)
    }

    @After fun stopKoinAfterTest() = stopKoin()
}