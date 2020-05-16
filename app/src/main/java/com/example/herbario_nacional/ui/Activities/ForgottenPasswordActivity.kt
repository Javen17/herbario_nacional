package com.example.herbario_nacional.ui.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.herbario_nacional.R
import com.example.herbario_nacional.models.EmailData
import com.example.herbario_nacional.ui.viewModels.CredentialsViewModel
import com.example.herbario_nacional.ui.viewModels.ProfileViewModel
import kotlinx.android.synthetic.main.activity_forgotten_password.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ForgottenPasswordActivity : AppCompatActivity() {

    //Calls crediatls repository to init reset passwor process
    private val myCredentialsViewModel : CredentialsViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgotten_password)

        back_btn.setOnClickListener {
            val intent = Intent(this@ForgottenPasswordActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }


        //Password reset
        reset_password_btn.setOnClickListener {

            val email: String = emailInput.text.toString()
            Log.i("RESET PASSWORD", "BUTTON EVENT");
            
            val object_f = EmailData(email);
            myCredentialsViewModel.resetPassword(object_f);

            Toast.makeText(this, "Si la dirección es correcta, recibiras un correo para cambiar tu contraseña dentro de poco", Toast.LENGTH_LONG).show();

            //myProfileViewModel.updateProfile(image, number_id, phone);
        }
    }




}
