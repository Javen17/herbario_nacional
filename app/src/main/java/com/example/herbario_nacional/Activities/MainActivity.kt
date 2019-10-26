package com.example.herbario_nacional.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.herbario_nacional.Fragments.NotificationsFragment
import com.example.herbario_nacional.Fragments.ProfileFragment
import com.example.herbario_nacional.Fragments.SearchFragment
import com.example.herbario_nacional.Fragments.SpecimensFragment
import com.example.herbario_nacional.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var textMessage: TextView

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_specimens -> {
                replaceFragment(SpecimensFragment())
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
                replaceFragment(ProfileFragment())
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

        // Fragment por defecto
        replaceFragment(SpecimensFragment())

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
