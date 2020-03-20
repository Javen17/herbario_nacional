package com.example.herbario_nacional.ui.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.herbario_nacional.R
import com.example.herbario_nacional.ui.viewModels.ProfileViewModel
import com.example.herbario_nacional.ui.viewModels.RegisterViewModel
import com.example.herbario_nacional.util.traveseAnyInput
import kotlinx.android.synthetic.main.activity_register.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private val registerViewModel: RegisterViewModel by viewModel()
    // private val profileViewModel: ProfileViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        back_btn.setOnClickListener {showActivity(NoLoginActivity::class.java)}
        login_label.setOnClickListener{showActivity(LoginActivity::class.java)}

        btnRegister.setOnClickListener{
            val date = Date()
            val formatter = SimpleDateFormat("yyyy/mm/dd hh:mm:ss", Locale.getDefault())
            val currentDate: String = formatter.format(date)

            if (layoutRegister.traveseAnyInput()) {
                Toast.makeText(applicationContext, getString(R.string.empty_fields_register), Toast.LENGTH_LONG).show()
            }
            else {
                registerViewModel.requestRegister(
                    first_name = nameInput.text.toString(),
                    last_name = lastnameInput.text.toString(),
                    username = usernameInput.text.toString(),
                    email = emailInput.text.toString(),
                    password = passwordInput.text.toString(),
                    is_staff = false,
                    is_active = true,
                    is_superuser = false,
                    date_joined = currentDate,
                    name = nameInput.text.toString(),
                    //groups = arrayOf(),
                    //user_permissions = arrayOf(),
                    last_login = null
                )
            }
        }

        registerViewModel.uiState.observe(this, Observer {
            val dataState = it ?: return@Observer
            if (dataState.result != null && !dataState.result.consumed){
                dataState.result.consume()?.let { result ->
                    if(result.result == "user added") {
                        Toast.makeText(applicationContext, getString(R.string.register_success), Toast.LENGTH_LONG).show()
                        showActivity(LoginActivity::class.java)
                    }
                }
            }

            if (dataState.error != null && !dataState.error.consumed){
                dataState.error.consume()?.let { error ->
                    Toast.makeText(applicationContext, resources.getString(error), Toast.LENGTH_LONG).show()
                }
            }
        })

        /*profileViewModel.uiState.observe(this, Observer {
            val dataState = it ?: return@Observer
            if (dataState.result != null && !dataState.result.consumed){
                dataState.result.consume()?.let { result ->
                    if(result.result == "Success") {
                        Toast.makeText(applicationContext, getString(R.string.register_success), Toast.LENGTH_LONG).show()
                        showActivity(LoginActivity::class.java)
                    }
                }
            }

            if (dataState.error != null && !dataState.error.consumed){
                dataState.error.consume()?.let { error ->
                    Toast.makeText(applicationContext, resources.getString(error), Toast.LENGTH_LONG).show()
                }
            }
        })*/
    }

    private fun showActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        this.finish()
    }
}