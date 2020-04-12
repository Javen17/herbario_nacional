package com.example.herbario_nacional.ui.Activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.herbario_nacional.R
import kotlinx.android.synthetic.main.activity_no_login.*

class NoLoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_login)
        close_btn.setOnClickListener { showActivity(MainActivity::class.java) }
        register_btn.setOnClickListener { showActivity(RegisterActivity::class.java) }
        login_btn.setOnClickListener { showActivity(LoginActivity::class.java) }
    }

    private fun showActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }
}