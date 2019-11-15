package com.example.herbario_nacional.ui.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.herbario_nacional.ui.Fragments.NotificationsFragment
import com.example.herbario_nacional.ui.Fragments.SearchFragment
import com.example.herbario_nacional.ui.Fragments.DataSheetFragment
import com.example.herbario_nacional.R
import com.example.herbario_nacional.ui.viewModels.CredentialsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val credentialsViewModel: CredentialsViewModel by viewModel()

    fun showDataSheetOption(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.setOnMenuItemClickListener(onMenuItemClickListener)
        popupMenu.inflate(R.menu.data_sheet_options)
        popupMenu.show()
    }

    private val onMenuItemClickListener = PopupMenu.OnMenuItemClickListener { item ->
        when (item.itemId) {
            R.id.plant_option -> {
                val intent = Intent(this@MainActivity, NewPlantActivity::class.java)
                startActivity(intent)
                finish()
                return@OnMenuItemClickListener true
            }
            R.id.fungus_option -> {
                val intent = Intent(this@MainActivity, NewPlantActivity::class.java)
                startActivity(intent)
                finish()
                return@OnMenuItemClickListener true
            }
        }
        false
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_data_sheet -> {
                replaceFragment(DataSheetFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_search -> {
                replaceFragment(SearchFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                replaceFragment(NotificationsFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                val intent = Intent(this@MainActivity, NoLoginActivity::class.java)
                startActivity(intent)
                finish()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.fragmentContainer, fragment)
        fragmentTransition.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        // Fragment por defecto
        replaceFragment(DataSheetFragment())

        val navView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        /*
        val recyclerView:RecyclerView = findViewById(R.id.plant_specimen_list)

        recyclerView.layoutManager=LinearLayoutManager(this, RecyclerView.VERTICAL,false)

        val plants = ArrayList<Plant>()

        plants.add(
            Plant(
                "Girasol",
                R.drawable.plant1
            )
        )
        plants.add(
            Plant(
                "Trompeta China Trepadora",
                R.drawable.plant2
            )
        )
        plants.add(
            Plant(
                "Galio Blanco",
                R.drawable.plant3
            )
        )

        val adapter = PlantAdapter(plants)

        recyclerView.adapter = adapter
        */
    }
}
