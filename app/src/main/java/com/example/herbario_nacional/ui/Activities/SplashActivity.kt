package com.example.herbario_nacional.ui.Activities

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.herbario_nacional.R
import com.example.herbario_nacional.ui.viewModels.MeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : AppCompatActivity() {

    private val meViewModel: MeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        meViewModel.uiState.observe(this, Observer {
            val dataState = it ?: return@Observer
            if (dataState.result != null && !dataState.result.consumed){
                dataState.result.consume()?.let { _ ->
                    showActivity(MainActivity::class.java)
                }
            }
            if (dataState.error != null && !dataState.error.consumed){
                dataState.error.consume()?.let { _ ->
                    showActivity(NoLoginActivity::class.java)
                }
            }
        })
    }

    private fun showActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        this.finish()
    }
}
