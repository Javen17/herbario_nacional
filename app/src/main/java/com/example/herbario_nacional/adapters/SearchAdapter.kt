package com.example.herbario_nacional.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.herbario_nacional.ui.Fragments.SearchFunghiFragment
import com.example.herbario_nacional.ui.Fragments.SearchPlantFragment

class SearchAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                SearchPlantFragment()
            }
            else -> {
                SearchFunghiFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {

        return when (position) {
            0 -> "Plantas"
            else -> {
                return "Hongos"
            }
        }
    }
}