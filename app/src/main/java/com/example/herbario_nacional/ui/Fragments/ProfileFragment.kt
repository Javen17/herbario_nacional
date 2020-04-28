package com.example.herbario_nacional.ui.Fragments


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import coil.api.load
import com.example.herbario_nacional.R
import com.example.herbario_nacional.base.BaseApplication
import com.example.herbario_nacional.preferences.AppPreferences
import com.example.herbario_nacional.ui.Activities.NoLoginActivity
import com.example.herbario_nacional.ui.viewModels.CredentialsViewModel
import com.example.herbario_nacional.ui.viewModels.MeViewModel
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {

    private val meViewModel: MeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var currentUser: Int

        profile_picture.load("https://api.adorable.io/avatars/200/25@adorable.png")

        meViewModel.uiState.observe(viewLifecycleOwner, Observer {
            val dataState = it ?: return@Observer
            if (dataState.result != null && !dataState.result.consumed){
                dataState.result.consume()?.let { result ->
                    currentUser = result.data.id
                    fullname.text = "${result.data.first_name} ${result.data.last_name}"
                    username.text = result.data.username
                    email.text = result.data.email
                }
            }
            if (dataState.error != null && !dataState.error.consumed){
                dataState.error.consume()?.let { error ->
                    Toast.makeText(context, resources.getString(error), Toast.LENGTH_LONG).show()
                }
            }
        })

        logout_btn.setOnClickListener {
            AppPreferences().remove(AppPreferences.Key.cookies)
            showActivity(NoLoginActivity::class.java)
            Toast.makeText(context, getString(R.string.logoutNotification), Toast.LENGTH_LONG).show()
        }
    }

    private fun showActivity(activityClass: Class<*>) {
        val intent = Intent(activity, activityClass)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        activity?.finish()
    }
}
