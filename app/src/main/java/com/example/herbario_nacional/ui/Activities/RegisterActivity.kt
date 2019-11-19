package com.example.herbario_nacional.ui.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.herbario_nacional.R
import com.example.herbario_nacional.ui.viewModels.RegisterViewModel
import com.example.herbario_nacional.util.traveseAnyInput
import kotlinx.android.synthetic.main.activity_register.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterActivity : AppCompatActivity() {

    private val registerViewModel: RegisterViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        back_btn.setOnClickListener {showActivity(NoLoginActivity::class.java)}
        login_label.setOnClickListener{showActivity(LoginActivity::class.java)}

        btnRegister.setOnClickListener{
            when(layoutRegister.traveseAnyInput()){
                true -> Toast.makeText(applicationContext, getString(R.string.empty_fields_register), Toast.LENGTH_LONG).show()
                false -> registerViewModel.requestRegister(nameInput.text.toString(), lastnameInput.text.toString(), phoneNumberInput.text.toString(), referenceNumberInput.text.toString(), usernameInput.text.toString(), emailInput.text.toString(), passwordInput.text.toString())
            }
        }

        registerViewModel.uiState.observe(this, Observer {
            val dataState = it ?: return@Observer
            if (dataState.status != null && !dataState.status.consumed){
                dataState.status.consume()?.let { status ->
                    if(status.status == "Success") Toast.makeText(applicationContext, getString(R.string.login_success), Toast.LENGTH_LONG).show()
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