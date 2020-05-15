package com.example.herbario_nacional.ui.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.afollestad.vvalidator.form
import com.example.herbario_nacional.R
import com.example.herbario_nacional.models.ErrorBody
import com.example.herbario_nacional.ui.viewModels.CredentialsViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.loading
import kotlinx.android.synthetic.main.activity_login.passwordInput
import kotlinx.android.synthetic.main.activity_login.usernameInput
import kotlinx.android.synthetic.main.activity_login.usernameTextInputLayout
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.back_btn
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val credentialsViewModel: CredentialsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        back_btn.setOnClickListener { showActivity(NoLoginActivity::class.java) }
        register_label.setOnClickListener{ showActivity(RegisterActivity::class.java) }
        forgot_password.setOnClickListener{ showActivity(ForgottenPasswordActivity::class.java) }

        formValidation()

        credentialsViewModel.uiState.observe(this, Observer {
            login_btn.visibility = View.INVISIBLE
            back_btn.visibility = View.INVISIBLE
            register_label.visibility = View.INVISIBLE
            loading.visibility = View.VISIBLE

            val dataState = it ?: return@Observer
            if (dataState.result != null && !dataState.result.consumed){
                dataState.result.consume()?.let { result ->
                    if(result.result == "success") {
                        Toast.makeText(applicationContext, getString(R.string.login_success), Toast.LENGTH_LONG).show()
                        showActivity(MainActivity::class.java)
                    }
                }
            }

            if (dataState.error != null && !dataState.error.consumed){
                dataState.error.consume()?.let { error ->
                    login_btn.visibility = View.VISIBLE
                    back_btn.visibility = View.VISIBLE
                    register_label.visibility = View.VISIBLE
                    loading.visibility = View.GONE

                    try {
                        val gson = Gson()
                        val errorResult = gson.fromJson(error, ErrorBody::class.java)
                        when(errorResult.result) {
                            "Something went wrong" -> showSnackbar("Nombre de usuario o contraseña incorrecta.")
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
        form {
            useRealTimeValidation()
            inputLayout(R.id.usernameTextInputLayout) {
                isNotEmpty().description("* Requerido")
            }
            inputLayout(R.id.passwordTextInputLayout) {
                isNotEmpty().description("* Requerido")
            }
            submitWith(R.id.login_btn) {result ->
                if (result.success()) {
                    credentialsViewModel.requestLogin(usernameInput.text.toString(), passwordInput.text.toString())
                }
            }
        }
    }

    private fun showSnackbar(message: String) {
        val snack = Snackbar.make(findViewById(R.id.login_layout), message, Snackbar.LENGTH_LONG)
        snack.show()
    }


    private fun showActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        this.finish()
    }
}
