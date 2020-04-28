package com.example.herbario_nacional.ui.Fragments


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.ArrayMap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import coil.api.load
import com.example.herbario_nacional.R
import com.example.herbario_nacional.models.MeData
import com.example.herbario_nacional.models.Profile
import com.example.herbario_nacional.preferences.AppPreferences
import com.example.herbario_nacional.ui.Activities.NoLoginActivity
import com.example.herbario_nacional.ui.viewModels.MeViewModel
import com.example.herbario_nacional.ui.viewModels.ProfileViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_profile.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment(), View.OnClickListener {

    private val meViewModel: MeViewModel by viewModel()

    private val myProfileViewModel: ProfileViewModel by viewModel()

    private val myAccountViewModel: MeViewModel by viewModel()

    private var profile: Profile? = null;
    private var account: MeData? = null;


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater!!.inflate(R.layout.fragment_profile, container, false)

        var profile_button: FloatingActionButton = view.findViewById(R.id.save_account_profile_btn);
        profile_button.setOnClickListener(this);

        var logout_btn: Button = view.findViewById(R.id.logout_btn);
        logout_btn.setOnClickListener {
            AppPreferences().remove(AppPreferences.Key.cookies)
            showActivity(NoLoginActivity::class.java)
            Toast.makeText(context, getString(R.string.logoutNotification), Toast.LENGTH_LONG).show()
        }

        // Inflate the layout for this fragment
        return view


    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var currentUser: Int?

        profile_picture.load("https://api.adorable.io/avatars/200/25@adorable.png")

        meViewModel.uiState.observe(viewLifecycleOwner, Observer {
            val dataState = it ?: return@Observer
            if (dataState.result != null && !dataState.result.consumed){
                dataState.result.consume()?.let { result ->
                    currentUser = result.data.id;
                    Toast.makeText(context, "Usuario actual: $currentUser", Toast.LENGTH_LONG).show()
                    fullname.text = "${result.data.first_name} ${result.data.last_name}"
                    username.text = result.data.username
                    email.text = result.data.email

                    //UPDATE FIELDS
                    first_name_update.setText(result.data.first_name)
                    last_name_update.setText(result.data.last_name)
                    username_update.setText(result.data.username)
                    email_update.setText(result.data.email)
                    phone_update.setText(result.data.profile!!.phone)
                    numb_ref_update.setText(result.data.profile.number_id)
                    profile = result.data.profile;
                    account = result.data;
                }
            }
            if (dataState.error != null && !dataState.error.consumed){
                dataState.error.consume()?.let { error ->
                    Toast.makeText(context, resources.getString(error), Toast.LENGTH_LONG).show()
                    showActivity(NoLoginActivity::class.java)
                }
            }
        })
    }

    private fun showActivity(activityClass: Class<*>) {
        val intent = Intent(activity, activityClass)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        activity?.finish()
    }



    override fun onClick(v: View?) {

        Toast.makeText(context, "UPDATING...", Toast.LENGTH_LONG).show();

        //Profile mutable params
        val profileParams: MutableMap<String, Any> = ArrayMap();
        profileParams["number_id"] = numb_ref_update.text.toString()
        profileParams["phone"] = phone_update.text.toString()
        //jsonParams["photo"] = profile!!.photo;
        profileParams["username"] = username_update.text.toString();


        //Account mutable params
        val accountParams: MutableMap<String, Any> =
            ArrayMap()
        accountParams["email"] = email_update.text.toString();
        accountParams["first_name"] = first_name_update.text.toString();
        accountParams["last_name"] = last_name_update.text.toString();
        accountParams["username"] = username_update.text.toString();


        //Account request body
        val AccountBody = myProfileViewModel.createJsonObject(accountParams);

        //Profile request body
        val ProfileBody = myProfileViewModel.createJsonObject(profileParams);

        //update profile
        myProfileViewModel.updateProfile(ProfileBody);

        //update account
        myAccountViewModel.updateAccount(AccountBody);

    }

}


