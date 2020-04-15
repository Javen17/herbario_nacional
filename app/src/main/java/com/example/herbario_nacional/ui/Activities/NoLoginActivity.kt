package com.example.herbario_nacional.ui.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.herbario_nacional.R
import com.example.herbario_nacional.ui.viewModels.MeViewModel
import kotlinx.android.synthetic.main.activity_no_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class NoLoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_login)

        register_btn.setOnClickListener { showActivity(RegisterActivity::class.java, false) }
        login_btn.setOnClickListener { showActivity(LoginActivity::class.java, false) }
        guess_btn.setOnClickListener { showActivity(MainActivity::class.java, true) }
    }

    private fun showActivity(activityClass: Class<*>, isFinish: Boolean) {
        val intent = Intent(this, activityClass)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        if (isFinish) finish()
    }
}