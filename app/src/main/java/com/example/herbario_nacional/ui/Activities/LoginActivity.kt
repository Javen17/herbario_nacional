package com.example.herbario_nacional.ui.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.herbario_nacional.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.back_btn

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        back_btn.setOnClickListener {
            val intent = Intent(this@LoginActivity, NoLoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        register_label.setOnClickListener{
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        forgot_password.setOnClickListener{
            val intent = Intent(this@LoginActivity, ForgottenPasswordActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
