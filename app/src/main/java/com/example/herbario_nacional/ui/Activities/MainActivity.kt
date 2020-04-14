package com.example.herbario_nacional.ui.Activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.herbario_nacional.R
import com.example.herbario_nacional.ui.Fragments.DataSheetFragment
import com.example.herbario_nacional.ui.Fragments.NotificationsFragment
import com.example.herbario_nacional.ui.Fragments.ProfileFragment
import com.example.herbario_nacional.ui.Fragments.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationItemView


class MainActivity : AppCompatActivity(), View.OnClickListener {



    companion object {
        var isLogedIn = false;
        var userAccount: Object? =null;

        //FRAGMENT DICTIONARY
        val dataSheetFragmentId: kotlin.Int = 0;
        val profileFragmentId: kotlin.Int = 1;
        val fungiFragmentId: kotlin.Int = 2;
        val notificationsFragmentId: kotlin.Int = 3;
        val plantsFragmentId: kotlin.Int = 4;
        val searchFragmentId: kotlin.Int = 5;
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //val navController : NavController = findNavController(R.id.navigationHost)
        //bottom_navigation.setupWithNavController(navController)

        //Init starting fragment
        changeFragment(dataSheetFragmentId);


        var profile_button = findViewById<BottomNavigationItemView>(R.id.noLoginActivity);
        profile_button.setOnClickListener(this);

        var dataSheet_button = findViewById<BottomNavigationItemView>(R.id.dataSheetFragment);
        dataSheet_button.setOnClickListener(this);

        var search_button = findViewById<BottomNavigationItemView>(R.id.searchFragment);
        search_button.setOnClickListener(this);

        var notifications_button = findViewById<BottomNavigationItemView>(R.id.notificationsFragment);
        notifications_button.setOnClickListener(this);

    }

    override fun onClick(v: View?) {
        //Toast.makeText(this, "onclick event", Toast.LENGTH_SHORT).show();


        if (v != null) {
            when(v.id){

                //ACCOUNT MANAGEMENT
                R.id.noLoginActivity -> {
                    //Toast.makeText(this, "Profile button triggered", Toast.LENGTH_LONG).show();
                    showActivity(NoLoginActivity::class.java);
                }

                //DATASHEET
                R.id.dataSheetFragment -> {
                    //Toast.makeText(this, "Profile button triggered", Toast.LENGTH_LONG).show();
                    changeFragment(dataSheetFragmentId);
                }

                //SEARCH
                R.id.searchFragment -> {
                    //Toast.makeText(this, "Profile button triggered", Toast.LENGTH_LONG).show();
                    changeFragment(searchFragmentId);
                }

                //NOTIFICATIONS
                R.id.notificationsFragment -> {
                    //Toast.makeText(this, "Profile button triggered", Toast.LENGTH_LONG).show();
                    changeFragment(notificationsFragmentId);
                }

            }
        }
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

       Log.i("ACTIVITY LAUNCH", "PROFILE ACTIVITY");
        if(activityClass.equals(NoLoginActivity::class.java) && isLogedIn){

                Log.i("FRAGMENT LAUNCH", "PROFILE FRAGMENT");
                changeFragment(profileFragmentId);

        }else{

            val intent = Intent(this, activityClass)

            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

        }
    }

    private fun changeFragment(fragment_indicator:kotlin.Int){

        var fragment: Fragment? = null

        when (fragment_indicator){

            //PROFILE FRAGMENT
            dataSheetFragmentId -> {
                fragment = DataSheetFragment();
            };

            profileFragmentId -> {
                fragment = ProfileFragment();
            };

            searchFragmentId -> {
                fragment = SearchFragment();
            };

            notificationsFragmentId -> {
                fragment = NotificationsFragment();
            };
        }


        if (fragment != null) {
            Log.i("FRAGMENT LAUNCHING", "LAUNCH!")
            val transaction = supportFragmentManager.beginTransaction();
            transaction.remove(DataSheetFragment());
            transaction.replace(R.id.navigationHost, fragment);
            transaction.addToBackStack(null);

            transaction.commit()
        }
    }

}
