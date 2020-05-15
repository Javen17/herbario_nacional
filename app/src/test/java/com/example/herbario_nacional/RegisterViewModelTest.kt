package com.example.herbario_nacional

import android.os.Build
import com.example.herbario_nacional.models.Register
import com.example.herbario_nacional.repo.RegisterRepository
import com.example.herbario_nacional.ui.viewModels.RegisterViewModel
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
class RegisterViewModelTest: KoinTest {

    private val registerRepository: RegisterRepository by inject()

    @Test
    fun registerViewmodelImpl_isCorrect(){
        val registerViewModel = RegisterViewModel(registerRepository)
        val expected = Register(
            "John",
            "Doe",
            "jdoe",
            "john@gmail.com",
            "superSecretPassword",
            is_staff = false,
            is_active = true,
            date_joined = "2020-05-10T18:46:19Z",
            groups = intArrayOf(1),
            is_superuser = false,
            last_login = null,
            name = "John"
        )

        val expected2 = Register(
            "John",
            "Doe",
            "jdoe",
            "john@gmail.com",
            "superSecretPassword",
            is_staff = false,
            is_active = true,
            date_joined = "2020-05-10T18:46:19Z",
            groups = intArrayOf(1),
            is_superuser = false,
            last_login = null,
            name = "John"
        )

        val dataReceived = runBlocking { registerViewModel.requestRegister(expected) }
        val dataReceived2 = runBlocking { registerViewModel.requestRegister(expected2) }

        assertNotNull(dataReceived)
        assertEquals(dataReceived, dataReceived2)
    }

    @After fun stopKoinAfterTest() = stopKoin()
}