package com.example.herbario_nacional.ui.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.herbario_nacional.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController : NavController = findNavController(R.id.navigationHost)
        bottom_navigation.setupWithNavController(navController)
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
