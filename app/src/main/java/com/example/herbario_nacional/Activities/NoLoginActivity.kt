package com.example.herbario_nacional.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.herbario_nacional.R
import kotlinx.android.synthetic.main.activity_no_login.*

class NoLoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_login)

        close_btn.setOnClickListener {
            val intent = Intent (this@NoLoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
