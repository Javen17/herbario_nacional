package com.example.herbario_nacional.ui.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.herbario_nacional.R
import com.example.herbario_nacional.ui.viewModels.CredentialsViewModel
import com.example.herbario_nacional.util.traveseAnyInput
import kotlinx.android.synthetic.main.activity_login.*
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

        btnLogin.setOnClickListener {
            when(layoutLogin.traveseAnyInput()){
                true -> Toast.makeText(applicationContext, getString(R.string.empty_fields), Toast.LENGTH_LONG).show()
                false -> credentialsViewModel.requestLogin(usernameInput.text.toString(), passwordInput.text.toString())
            }
        }

        credentialsViewModel.uiState.observe(this, Observer {
            val dataState = it ?: return@Observer
            if (dataState.result != null && !dataState.result.consumed){
                dataState.result.consume()?.let { result ->
                    if(result.result == "success") {
                        Toast.makeText(
                            applicationContext,
                            getString(R.string.login_success),
                            Toast.LENGTH_LONG
                        ).show()
                        // showActivity()
                    }
                }
            }
            if (dataState.error != null && !dataState.error.consumed){
                dataState.error.consume()?.let { error ->
                    Toast.makeText(applicationContext, resources.getString(error), Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun showActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        this.finish()
    }
}
