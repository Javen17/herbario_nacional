package com.example.herbario_nacional.ui.Activities

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.library.BuildConfig
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.herbario_nacional.R
import kotlinx.android.synthetic.main.activity_main.*
import com.example.herbario_nacional.models.AndroidDevice
import com.example.herbario_nacional.preferences.AppPreferences
import com.example.herbario_nacional.ui.viewModels.AndroidDeviceViewModel
import com.example.herbario_nacional.ui.viewModels.MeViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*
import androidx.lifecycle.Observer
import com.example.herbario_nacional.models.AndroidDeviceUpdate
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    private val meViewModel: MeViewModel by viewModel()
    private val androidDeviceViewModel: AndroidDeviceViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController : NavController = findNavController(R.id.navigationHost)
        bottom_navigation.setupWithNavController(navController)

        val date = Date()
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("es"))
        val currentDate: String = formatter.format(date)
        val androidId: String = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)

        var currentUser: Int by Delegates.observable(0) { _, _, newValue ->
            androidDeviceViewModel.searchUiState.observe(this, Observer {
                val dataState = it ?: return@Observer
                if (dataState.result != null && !dataState.result.consumed){
                    dataState.result.consume()?.let { result ->
                        result.forEach { androidDevice ->
                            androidDeviceViewModel.requestPutAndroiDevice(androidDevice.id,  AndroidDeviceUpdate(newValue))
                        }
                    }
                }
                if (dataState.error != null && !dataState.error.consumed){
                    dataState.error.consume()?.let { }
                }
            })
        }
        print(currentUser)

        androidDeviceViewModel.uiState.observe(this, Observer {
            val dataState = it ?: return@Observer
            if (dataState.result != null && !dataState.result.consumed){
                dataState.result.consume()?.let { }
            }
            if (dataState.error != null && !dataState.error.consumed){
                dataState.error.consume()?.let { }
            }
        })

        meViewModel.uiState.observe(this, Observer {
            val dataState = it ?: return@Observer
            if (dataState.result != null && !dataState.result.consumed){
                dataState.result.consume()?.let { result ->
                    currentUser = result.data.id
                    FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener(OnCompleteListener { task ->
                        if (!task.isSuccessful) {
                            return@OnCompleteListener
                        }
                        val token = task.result?.token

                        if (token != null && token != AppPreferences().get(AppPreferences.Key.TokenFCM, "")) {
                            AppPreferences().remove(AppPreferences.Key.TokenFCM)
                            AppPreferences().put(AppPreferences.Key.TokenFCM, token)
                            androidDeviceViewModel.requestPostAndroiDevice(
                                AndroidDevice(
                                    application_id = null,
                                    user = result.data.id,
                                    registration_id = token,
                                    device_id = null,
                                    name = androidId,
                                    date_create = currentDate,
                                    cloud_message_type = "FCM",
                                    active = true
                                )
                            )
                        }
                        else {
                            androidDeviceViewModel.searchByAndroidDeviceId(androidId)
                        }
                    })
                }
            }
            if (dataState.error != null && !dataState.error.consumed){
                dataState.error.consume()?.let { }
            }
        })
    }

    fun showDataSheetOption(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.setOnMenuItemClickListener(onMenuItemClickListener)
        popupMenu.inflate(R.menu.data_sheet_options)
        popupMenu.show()
    }

    private val onMenuItemClickListener = PopupMenu.OnMenuItemClickListener { item ->
        when (item.itemId) {
            R.id.plant_option -> {
                showActivity(NewPlantActivity::class.java)
                return@OnMenuItemClickListener true
            }
            R.id.fungus_option -> {
                showActivity(NewFungusActivity::class.java)
                return@OnMenuItemClickListener true
            }
        }
        false
    }

    private fun showActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        this.finish()
    }
}
