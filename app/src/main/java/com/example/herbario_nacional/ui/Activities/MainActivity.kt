package com.example.herbario_nacional.ui.Activities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.herbario_nacional.R
import com.example.herbario_nacional.base.BaseApplication
import com.example.herbario_nacional.models.AndroidDevice
import com.example.herbario_nacional.preferences.AppPreferences
import com.example.herbario_nacional.ui.viewModels.AndroidDeviceViewModel
import com.example.herbario_nacional.ui.viewModels.MeViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {
    private val meViewModel: MeViewModel by viewModel()
    private val androidDeviceViewModel: AndroidDeviceViewModel by viewModel()

    var currentUser: Int = 0

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(this).registerReceiver((getToken), IntentFilter("TokenFCM"))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main);
        val navController : NavController = findNavController(R.id.navigationHost)
        bottom_navigation.setupWithNavController(navController)

        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            val token = task.result?.token

            if (token != null) {
                AppPreferences().put(AppPreferences.Key.TokenFCM, token)
            }
        })

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
                }
            }
            if (dataState.error != null && !dataState.error.consumed){
                dataState.error.consume()?.let { }
            }
        })
    }

    private val getToken: BroadcastReceiver = object : BroadcastReceiver() {
        val date = Date()
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale("es"))
        val currentDate: String = formatter.format(date)

        override fun onReceive(context: Context, intent: Intent) {
            val token = intent.extras?.getString("NewTokenFCM")
            if (AppPreferences().get(AppPreferences.Key.TokenFCM, "") !=  token) {
                androidDeviceViewModel.requestPostAndroiDevice(
                    AndroidDevice(
                        user = currentUser,
                        registration_id = token!!,
                        name = getMac(),
                        date_create = currentDate,
                        cloud_message_type = "FCM",
                        active = true
                    )
                )
            }
        }
    }

    private fun getMac(): String {
        val manager = BaseApplication.context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val information = manager.connectionInfo
        return information.macAddress.toUpperCase(Locale.ROOT)
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
