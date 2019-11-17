package com.example.herbario_nacional.ui.Activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.herbario_nacional.R
import com.example.herbario_nacional.ui.viewModels.CredentialsViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val credentialsViewModel: CredentialsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController : NavController = findNavController(R.id.navigationHost)
        bottom_navigation.setupWithNavController(navController)

        credentialsViewModel.uiState.observe(this, Observer {
            val dataState = it ?: return@Observer
            if (dataState.sessionToken != null && !dataState.sessionToken.consumed){
                dataState.sessionToken.consume()?.let {
                }
            }
            if (dataState.error != null && !dataState.error.consumed){
                dataState.error.consume()?.let { error ->
                    Toast.makeText(this, resources.getString(error), Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}
