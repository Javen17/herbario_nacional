package com.example.herbario_nacional.ui.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.afollestad.vvalidator.form
import com.example.herbario_nacional.R
import com.example.herbario_nacional.models.ErrorBody
import com.example.herbario_nacional.models.Register
import com.example.herbario_nacional.ui.viewModels.RegisterViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.back_btn
import kotlinx.android.synthetic.main.activity_register.passwordInput
import kotlinx.android.synthetic.main.activity_register.usernameInput
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private val registerViewModel: RegisterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        back_btn.setOnClickListener { showActivity(NoLoginActivity::class.java) }
        login_label.setOnClickListener { showActivity(LoginActivity::class.java) }

        formValidation()

        registerViewModel.uiState.observe(this, Observer {
            loading.visibility = View.VISIBLE
            back_btn.visibility = View.INVISIBLE
            login_label.visibility = View.INVISIBLE
            register_user_btn.visibility = View.INVISIBLE

            val dataState = it ?: return@Observer
            if (dataState.result != null && !dataState.result.consumed) {
                dataState.result.consume()?.let { result ->
                    if (result.result == "user added") {
                        Toast.makeText(applicationContext, getString(R.string.register_success), Toast.LENGTH_LONG).show()
                        showActivity(LoginActivity::class.java)
                    }
                }
            }

            if (dataState.error != null && !dataState.error.consumed) {
                dataState.error.consume()?.let { error ->
                    loading.visibility = View.GONE
                    back_btn.visibility = View.VISIBLE
                    login_label.visibility = View.VISIBLE
                    register_user_btn.visibility = View.VISIBLE

                    try {
                        val gson = Gson()
                        val errorResult = gson.fromJson(error, ErrorBody::class.java)
                        when(errorResult.result) {
                            "email is invalid" -> emailTextInputLayout.error = "Correo electrónico existente"
                            "username is invalid" -> usernameTextInputLayout.error = "Nombre de usuario existente"
                            "username and email are invalid" -> {
                                emailTextInputLayout.error = "Correo electrónico existente"
                                usernameTextInputLayout.error = "Nombre de usuario existente"
                            }
                        }
                    } catch (e: JsonSyntaxException) {
                        e.printStackTrace()
                        showSnackbar(error)
                    }
                }
            }
        })
    }

    private fun formValidation() {
        val date = Date()
        val formatter = SimpleDateFormat("yyyy/MM/dd hh:mm:ss", Locale("es"))
        val currentDate: String = formatter.format(date)

        form {
            useRealTimeValidation()
            inputLayout(R.id.nameTextInputLayout) {
                isNotEmpty().description("* Requerido")
            }
            inputLayout(R.id.lastnameTextInputLayout) {
                isNotEmpty().description("* Requerido")
            }
            inputLayout(R.id.usernameTextInputLayout) {
                isNotEmpty().description("* Requerido")
            }
            inputLayout(R.id.emailTextInputLayout) {
                isNotEmpty().description("* Requerido")
                isEmail().description("Ingresa un correo electrónico válido")
            }
            inputLayout(R.id.passwordTextInputLayout) {
                isNotEmpty().description("* Requerido")
                length().atLeast(8).description("La contraseña debe contener al menos 8 caracteres")
                matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[!@#\$&*~]).{8,}\$").description("La contraseña debe contener mayúsculas, minúsculas, números y caracteres especiales")
            }

            submitWith(R.id.register_user_btn) {result ->
                if (result.success()) {
                    registerViewModel.requestRegister(
                        Register(
                            first_name = nameInput.text.toString(),
                            last_name = lastnameInput.text.toString(),
                            username = usernameInput.text.toString().toLowerCase(Locale.ROOT).trim(),
                            email = emailInput.text.toString().toLowerCase(Locale.ROOT),
                            password = passwordInput.text.toString(),
                            date_joined = currentDate,
                            name = nameInput.text.toString()
                        )
                    )
                }
            }
        }
    }

    private fun showSnackbar(message: String) {
        val snack = Snackbar.make(findViewById(R.id.register_layout), message, Snackbar.LENGTH_LONG)
        snack.show()
    }

    private fun showActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        this.finish()
    }
}
