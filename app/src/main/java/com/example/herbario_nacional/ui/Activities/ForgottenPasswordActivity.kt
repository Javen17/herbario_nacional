package com.example.herbario_nacional.ui.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.herbario_nacional.R
import kotlinx.android.synthetic.main.activity_forgotten_password.*

class ForgottenPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgotten_password)

        back_btn.setOnClickListener {
            val intent = Intent(this@ForgottenPasswordActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
